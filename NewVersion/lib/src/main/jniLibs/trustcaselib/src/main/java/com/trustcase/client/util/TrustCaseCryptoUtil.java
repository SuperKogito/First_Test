package com.trustcase.client.util;

import org.abstractj.kalium.SodiumConstants;
import org.abstractj.kalium.crypto.Hash;
import org.abstractj.kalium.crypto.Random;
import org.abstractj.kalium.encoders.Hex;
import org.abstractj.kalium.keys.KeyPair;

import java.util.UUID;

/**
 * Small utility class to allow clients use some crypto functions without initializing full-blown TrustCaseClient
 */
public class TrustCaseCryptoUtil {
    private static final int KEY_STRETCH_ITERATIONS = 10000;

    private static Hash hash = new Hash();
    private static Hex hex = new Hex();
    private static Random random = new Random();

    public static String hash(String value) {
        return hash.sha256(value, hex);
    }

    /**
     * Create new key for symmetric encryption
     */
    public static String generateSymmetricKey() {
        byte[] symmetricKey = random.randomBytes(SodiumConstants.SECRETKEY_BYTES);
        return hex.encode(symmetricKey);
    }

    /**
     * Create new public/private key pair for asymmetric encryption
     */
    public static String[] generateKeyPair() {
        KeyPair keyPair = new KeyPair();
        return new String[] {
                hex.encode(keyPair.getPrivateKey().toBytes()),
                hex.encode(keyPair.getPublicKey().toBytes())
        };
    }

    /**
     * Generate new random UUID as string
     */
    public static String randomUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Create new symmetric key from given string password using pbkdf2-hmac-sha256 algorithm, given salt and
     * predefined number of iterations
     */
    public static String symmetricKeyFromPassword(String password, String salt) {
        return hash.pbkdf2_sha256(password, hex, salt.getBytes(), KEY_STRETCH_ITERATIONS);
    }
}