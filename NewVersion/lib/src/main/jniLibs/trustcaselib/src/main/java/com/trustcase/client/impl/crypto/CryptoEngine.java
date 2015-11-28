package com.trustcase.client.impl.crypto;


import com.trustcase.client.api.enums.EncryptionType;
import com.trustcase.client.api.exceptions.EncryptionException;

import org.abstractj.kalium.SodiumConstants;
import org.abstractj.kalium.crypto.Box;
import org.abstractj.kalium.crypto.Hash;
import org.abstractj.kalium.crypto.Random;
import org.abstractj.kalium.crypto.SecretBox;
import org.abstractj.kalium.crypto.Util;
import org.abstractj.kalium.encoders.Hex;
import org.abstractj.kalium.keys.KeyPair;
import org.abstractj.kalium.keys.PrivateKey;
import org.abstractj.kalium.keys.PublicKey;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

/**
 * Cryptography functions implementation.
 * Based on NaCl/Salt library.
 *
 * @author audrius
 */
public class CryptoEngine {

    private static int STREAM_BLOCK_SIZE = 1048576;
    private static int ENC_KEY_SIZE = 48;

    private KeyManager keyManager;
    private Hex hex;
    private Hash hash;
    private Random random;

    public CryptoEngine() {
        this.hex = new Hex();
        this.hash = new Hash();
        this.random = new Random();
    }

    public void init(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    public String[] generateKeyPair() {
        KeyPair keyPair = new KeyPair();
        return new String[]{keyPair.getPrivateKey().toString(), keyPair.getPublicKey().toString()};
    }

    public void encryptStream(InputStream in, OutputStream out, String symmetricKey)
            throws Exception {

        byte[] nonce = random.randomBytes(SodiumConstants.NONCE_BYTES);
        out.write(nonce);

        SecretBox box = new SecretBox(symmetricKey, hex);
        byte[] buffer = new byte[STREAM_BLOCK_SIZE];
        int count;
        while ((count = in.read(buffer)) > 0) {
            byte[] encrypted = box.encrypt(nonce, Util.slice(buffer, 0, count));
            out.write(encrypted);
        }
    }

    public void decryptStream(InputStream in, OutputStream out, String symmetricKey)
            throws Exception {

        byte[] nonce = new byte[SodiumConstants.NONCE_BYTES];
        readBytes(in, nonce);

        SecretBox box = new SecretBox(symmetricKey, hex);
        byte[] buffer = new byte[STREAM_BLOCK_SIZE + SodiumConstants.BOXZERO_BYTES];
        int count;
        while ((count = in.read(buffer)) > 0) {
            out.write(box.decrypt(nonce, Util.slice(buffer, 0, count)));
        }
    }

    private void readBytes(InputStream stream, byte[] array) throws IOException {
        if (stream == null || array == null) {
            throw new IllegalStateException("Null arguments to readBytes");
        }
        int size = stream.read(array);
        if (size != array.length) {
            throw new IllegalStateException(String.format("Failed to read bytes from stream. Expected %d, got %d",
                    array.length, size));
        }
    }

    private byte[] generateKeyBlock(PrivateKey privateKey, byte[] nonce, byte[] symmetricKey,
                                    String roomID, Collection<String> recipients) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        for (String jid : keyManager.getParticipantJIDs(roomID)) {
            if (recipients == null || recipients.contains(jid)) {
                PublicKey publicKey = keyManager.getParticipantPublicKey(roomID, jid);
                Box box = new Box(publicKey, privateKey);
                byte[] cipher = box.encrypt(nonce, symmetricKey);
                output.write(cipher);
            } else {
                output.write(random.randomBytes(ENC_KEY_SIZE));
            }
        }
        return output.toByteArray();
    }

    public byte[] encryptMessage(String message, EncryptionType encryptionType, String roomID,
                                 Collection<String> recipients) throws EncryptionException {
        if (keyManager == null) {
            throw new EncryptionException("Not initialized properly, no valid KeyManager");
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] msgBytes = message.getBytes("UTF-8");
            output.write(intToByteArray(encryptionType.ordinal())); // version
            output.write(intToByteArray(0)); // metadata
            output.write(intToByteArray(0)); // metadata

            byte[] nonce = random.randomBytes(SodiumConstants.NONCE_BYTES);
            byte[] symmetricKey, keyBlock;

            switch (encryptionType) {
                case UNENCRYPTED:
                    output.write(msgBytes);
                    break;

                case SYMMETRIC_KEY:
                    output.write(nonce); // nonce
                    PublicKey publicKey = keyManager.getRoomKey(roomID);
                    if (publicKey == null) {
                        throw new EncryptionException("No room key for symmetric encryption available!");
                    }
                    SecretBox secretBox = new SecretBox(publicKey.toBytes());
                    byte[] encryptedMessage = secretBox.encrypt(nonce, msgBytes);
                    output.write(encryptedMessage); // message
                    break;

                case PUBLIC_KEY:
                case NO_KEY:
                    symmetricKey = random.randomBytes(SodiumConstants.SECRETKEY_BYTES);
                    keyBlock = generateKeyBlock(keyManager.getPrivateKey(), nonce, symmetricKey, roomID, recipients);
                    output.write(intToByteArray(keyBlock.length / ENC_KEY_SIZE)); // Number of keys
                    output.write(nonce); // nonce
                    if (encryptionType == EncryptionType.PUBLIC_KEY) {
                        output.write(keyManager.getPublicKey().toBytes()); // sender's public key
                    }
                    output.write(keyBlock); // block of symmetric key encrypted with all public keys

                    secretBox = new SecretBox(symmetricKey);
                    encryptedMessage = secretBox.encrypt(nonce, msgBytes);
                    output.write(encryptedMessage); // message
                    break;
            }

            return output.toByteArray();
        } catch (Exception ex) {
            throw new EncryptionException("Encryption failed", ex);
        }
    }

    private byte[] readBytes(InputStream input, int no) throws IOException {
        byte[] array = new byte[no];
        readBytes(input, array);
        return array;
    }

    private byte[] intToByteArray(int a) {
        byte[] ret = new byte[4];
        ret[3] = (byte) (a & 0xFF);
        ret[2] = (byte) ((a >> 8) & 0xFF);
        ret[1] = (byte) ((a >> 16) & 0xFF);
        ret[0] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }

    private int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
    }

    public String decryptMessage(byte[] encryptedMessage, String roomID)
            throws EncryptionException {
        if (keyManager == null) {
            throw new EncryptionException("Not initialized properly, no valid KeyManager");
        }
        ByteArrayInputStream input = new ByteArrayInputStream(encryptedMessage);
        try {
            // first 4 bytes for version
            EncryptionType version = EncryptionType.values()[byteArrayToInt(readBytes(input, 4))];

            // next 8 bytes reserved for metadata
            readBytes(input, 8); // Metadata

            String strMessage = null;
            byte[] buffer, nonce, symmetricKey = null;
            PublicKey otherKey;
            int keyPosition, noKeys;

            switch (version) {

                case UNENCRYPTED:
                    buffer = new byte[input.available()];
                    readBytes(input, buffer);
                    strMessage = new String(buffer, "UTF-8");
                    break;

                case SYMMETRIC_KEY:
                    nonce = readBytes(input, SodiumConstants.NONCE_BYTES);
                    buffer = new byte[input.available()];
                    readBytes(input, buffer);
                    symmetricKey = keyManager.getRoomKey(roomID).toBytes();
                    SecretBox secretBox = new SecretBox(symmetricKey);
                    byte[] decryptedData = secretBox.decrypt(nonce, buffer);
                    strMessage = new String(decryptedData, "UTF-8");
                    break;

                case PUBLIC_KEY:
                    noKeys = byteArrayToInt(readBytes(input, 4));
                    nonce = readBytes(input, SodiumConstants.NONCE_BYTES);
                    otherKey = new PublicKey(readBytes(input, SodiumConstants.SECRETKEY_BYTES));
                    Box box = new Box(otherKey, keyManager.getPrivateKey());
                    keyPosition = keyManager.getMyPositionInRoom(roomID);
                    for (int i = 0; i < noKeys; i++) {
                        byte[] encKey = readBytes(input, ENC_KEY_SIZE);
                        if (symmetricKey == null && (keyPosition < 0 || keyPosition == i)) {
                            try {
                                symmetricKey = box.decrypt(nonce, encKey);
                            } catch (Exception ex) {
                                // Ignore
                            }
                        }
                    }
                    if (symmetricKey == null) {
                        throw new EncryptionException("No valid symmetric key found in the message");
                    }
                    secretBox = new SecretBox(symmetricKey);
                    buffer = new byte[input.available()];
                    readBytes(input, buffer);
                    decryptedData = secretBox.decrypt(nonce, buffer);
                    strMessage = new String(decryptedData, "UTF-8");
                    break;

                case NO_KEY:
                    noKeys = byteArrayToInt(readBytes(input, 4));
                    nonce = readBytes(input, SodiumConstants.NONCE_BYTES);
                    List<String> participants = keyManager.getParticipantJIDs(roomID);
                    keyPosition = keyManager.getMyPositionInRoom(roomID);
                    for (int i = 0; i < noKeys; i++) {
                        byte[] encKey = readBytes(input, ENC_KEY_SIZE);
                        if (symmetricKey == null && (keyPosition < 0 || keyPosition == i)) {
                            for (String jid : participants) {
                                PublicKey publicKey = keyManager.getParticipantPublicKey(roomID, jid);
                                box = new Box(publicKey, keyManager.getPrivateKey());
                                try {
                                    symmetricKey = box.decrypt(nonce, encKey);
                                    break;
                                } catch (Exception ex) {
                                    // Ignore
                                }
                            }
                        }
                    }
                    if (symmetricKey == null) {
                        throw new EncryptionException("No valid symmetric key found in the message");
                    }
                    secretBox = new SecretBox(symmetricKey);
                    buffer = new byte[input.available()];
                    readBytes(input, buffer);
                    decryptedData = secretBox.decrypt(nonce, buffer);
                    strMessage = new String(decryptedData, "UTF-8");
                    break;
            }
            return strMessage;
        } catch (Exception ex) {
            throw new EncryptionException("Decryption failed", ex);
        }
    }

    public String hash(String value) {
        return hash.sha256(value, hex);
    }

    public String hash(byte[] bytes) {
        byte[] hashed = hash.sha256(bytes);
        return hex.encode(hashed);
    }

    public String generateSymmetricKey() {
        byte[] symmetricKey = random.randomBytes(SodiumConstants.SECRETKEY_BYTES);
        return hex.encode(symmetricKey);
    }

    public byte[] encryptSymmetrically(String data, String key) throws EncryptionException {
        try {
            byte[] nonce = random.randomBytes(SodiumConstants.NONCE_BYTES);
            SecretBox box = new SecretBox(key, hex);
            byte[] cipher = box.encrypt(nonce, data.getBytes("UTF-8"));
            return Util.merge(nonce, cipher);
        } catch (Exception ex) {
            throw new EncryptionException("Encryption failed", ex);
        }
    }

    public String decryptSymmetrically(byte[] encData, String key) throws EncryptionException {
        try {
            byte[] nonce = Util.slice(encData, 0, SodiumConstants.NONCE_BYTES);
            byte[] cipher = Util.slice(encData, SodiumConstants.NONCE_BYTES, encData.length);
            SecretBox box = new SecretBox(key, hex);
            byte[] decData = box.decrypt(nonce, cipher);
            return new String(decData, "UTF-8");
        } catch (Exception ex) {
            throw new EncryptionException("Decryption failed", ex);
        }
    }
}