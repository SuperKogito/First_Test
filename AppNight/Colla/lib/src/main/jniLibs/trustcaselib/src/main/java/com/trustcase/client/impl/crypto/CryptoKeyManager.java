package com.trustcase.client.impl.crypto;

import com.trustcase.client.api.responses.Identity;

import org.abstractj.kalium.encoders.Hex;
import org.abstractj.kalium.keys.KeyPair;
import org.abstractj.kalium.keys.PrivateKey;
import org.abstractj.kalium.keys.PublicKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages public key for encryption/decryption.
 * Known keys are kept in memory for quick access.
 * Other keys will be loaded via provided KeyAccessor when needed.
 */
public class CryptoKeyManager implements KeyManager {
    // Keep known public keys in memory for quick access: roomID -> (jid -> public key)
    private Map<String, Map<String, PublicKey>> keyCache;
    private Map<String, PublicKey> roomKeyCache;

    private KeyPair ownKeys;

    private String ownJid;

    private KeyAccessor keyAccessor;

    public CryptoKeyManager() {
        keyCache = new HashMap<>();
        roomKeyCache = new HashMap<>();
    }

    public void init(KeyAccessor keyAccessor, String jid, String privateKey, String publicKey) {
        this.keyAccessor = keyAccessor;
        this.ownJid = jid;
        this.ownKeys = new KeyPair(privateKey, publicKey, new Hex());
    }

    public void invalidateRoom(String roomId) {
        keyCache.remove(roomId);
    }

    public String getJid() {
        return ownJid;
    }

    @Override
    public PublicKey getPublicKey() {
        return ownKeys == null ? null : ownKeys.getPublicKey();
    }

    @Override
    public PrivateKey getPrivateKey() {
        return ownKeys == null ? null : ownKeys.getPrivateKey();
    }

    @Override
    public PublicKey getRoomKey(String roomId) {
        if (!roomKeyCache.containsKey(roomId)) {
            cacheRoomKey(roomId, keyAccessor.getRoomKey(roomId));
        }
        return roomKeyCache.get(roomId);
    }

    public void cachePublicKeys(String roomId, List<Identity> participants) {
        HashMap<String, PublicKey> keys = new LinkedHashMap<>();
        for (Identity identity : participants) {
            keys.put(identity.jid, new PublicKey(identity.public_key));
        }
        keyCache.put(roomId, keys);
    }

    public void cacheRoomKey(String roomId, String roomKey) {
        if (roomKey != null) {
            roomKeyCache.put(roomId, new PublicKey(roomKey));
        }
    }

    @Override
    public List<String> getParticipantJIDs(String roomId) {
        if (!keyCache.containsKey(roomId)) {
            cachePublicKeys(roomId, keyAccessor.getPublicKeys(roomId));
        }
        return new ArrayList<>(keyCache.get(roomId).keySet());
    }

    @Override
    public PublicKey getParticipantPublicKey(String roomId, String jid) {
        if (!keyCache.containsKey(roomId)) {
            cachePublicKeys(roomId, keyAccessor.getPublicKeys(roomId));
        }
        return keyCache.get(roomId).get(jid);
    }

    @Override
    public int getMyPositionInRoom(String roomId) {
        int pos = -1;
        for (String jid : getParticipantJIDs(roomId)) {
            pos++;
            if (jid != null && jid.equals(ownJid)) {
                return pos;
            }
        }
        return -1;
    }

    @Override
    public String getContactProfileKey(String jid) {
        return keyAccessor.getProfileKey(jid);
    }
}
