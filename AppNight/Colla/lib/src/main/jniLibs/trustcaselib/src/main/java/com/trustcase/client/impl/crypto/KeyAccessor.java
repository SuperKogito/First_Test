package com.trustcase.client.impl.crypto;

import com.trustcase.client.api.responses.Identity;

import java.util.List;

/**
 * Accessor interface. Its methods are called as callbacks from CryptoEngine when it needs to get keys of participants.
 *
 * Created by audrius on 14.11.14.
 */
public interface KeyAccessor {
    /**
     * Return list of public keys of all participants in given room.
     * Keys must be returned in correct order (as in the order of room participants in which they were created)
     */
    List<Identity> getPublicKeys(String roomId);

    /**
     * Return symmetric key for decryption of one particular participant's profile or null if participant is
     * not known or has not published his profile key.
     */
    String getProfileKey(String jid);

    /**
     * Return symmetric encryption key of one particular room or null if roomId is not known or symmetric encryption
     * is not used for this room.
     */
    String getRoomKey(String roomId);

    /**
     * Return symmetric encryption key for trust circle of one particular trust broker or null if user with given jid
     * is not known or is not a trust broker.
     */
    String getTrustCircleKey(String jid);
}
