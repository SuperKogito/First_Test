package com.trustcase.client.impl.crypto;

import org.abstractj.kalium.keys.PrivateKey;
import org.abstractj.kalium.keys.PublicKey;

import java.util.List;

/**
 * Interface for key manager - class which manages and retrieves keys for CryptoEngine
 */
public interface KeyManager {
    PublicKey getPublicKey();

    PrivateKey getPrivateKey();

    PublicKey getRoomKey(String roomId);

    List<String> getParticipantJIDs(String roomId);

    PublicKey getParticipantPublicKey(String roomId, String jid);

    int getMyPositionInRoom(String roomId);

    String getContactProfileKey(String jid);
}
