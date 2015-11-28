package com.trustcase.client.api.messages;

/**
 * Wrapper for passing and retrieving profile information to and from TrustCase server
 */
public class ProfileWrapper {
    private String jid;
    private String publicKey;
    private Profile profile;

    public ProfileWrapper() {
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String toString() {
        return "ProfileWrapper{" +
                "jid='" + jid + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }
}
