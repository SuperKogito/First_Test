package com.trustcase.client.util;

import com.trustcase.client.TrustCaseClient;
import com.trustcase.client.api.enums.TrustCaseRole;
import com.trustcase.client.api.responses.Contact;
import com.trustcase.client.api.responses.Identity;
import com.trustcase.client.impl.GsonProvider;

/**
 * Common trustcase client utilities.
 * 
 * @author Gunther Klein
 */
public final class TrustCaseClientUtils {

    private TrustCaseClientUtils() {
        // not meant to be instantiated
    }

    public static Identity toIdentity(TrustCaseClient client) {
        return toIdentity(client, TrustCaseRole.MOBILE_USER);
    }

    public static Identity toIdentity(TrustCaseClient client, TrustCaseRole role) {
        return new Identity(client.getJid(), client.getKeyPair().getPublicKey(), role);
    }

    public static Identity toIdentity(Contact contact, TrustCaseRole role) {
        return new Identity(contact.getJid(), contact.getPublicKey(), role);
    }

    public static Identity toIdentity(Contact contact) {
        return new Identity(contact.getJid(), contact.getPublicKey());
    }

    public static <T> T parseJson(String jsonString, Class<T> type) {
        return GsonProvider.getInstance().getStandardGson().fromJson(jsonString, type);
    }

}
