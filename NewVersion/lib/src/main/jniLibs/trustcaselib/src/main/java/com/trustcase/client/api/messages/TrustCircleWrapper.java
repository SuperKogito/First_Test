package com.trustcase.client.api.messages;

import com.trustcase.client.api.enums.TrustMode;

/**
 * Representation of one item in trust circle
 */
public class TrustCircleWrapper {
    private String jid;
    private String publicKey;
    private Profile profile;
    private TrustMode trustMode;

    public TrustCircleWrapper(String jid, TrustMode trustMode, Profile profile) {
        this.jid = jid;
        this.profile = profile;
        this.trustMode = trustMode;
    }

    public TrustCircleWrapper(String jid, TrustMode trustMode, Profile profile, String publicKey) {
        this(jid, trustMode, profile);
        this.publicKey = publicKey;
    }

    public String getJid() {
        return jid;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public Profile getProfile() {
        return profile;
    }

    public TrustMode getTrustMode() {
        return trustMode;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setTrustMode(TrustMode trustMode) {
        this.trustMode = trustMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrustCircleWrapper wrapper = (TrustCircleWrapper) o;

        if (jid != null ? !jid.equals(wrapper.jid) : wrapper.jid != null) return false;
        if (profile != null ? !profile.equals(wrapper.profile) : wrapper.profile != null) return false;
        return trustMode == wrapper.trustMode;

    }

    @Override
    public int hashCode() {
        int result = jid != null ? jid.hashCode() : 0;
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        result = 31 * result + (trustMode != null ? trustMode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProfileWrapper{" +
                "jid='" + jid + '\'' +
                ", trustMode='" + trustMode + '\'' +
                ", profile='" + profile + '\'' +
                '}';
    }
}
