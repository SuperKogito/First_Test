package com.trustcase.client.api;

/**
 * Client credentials - minimal set of info needed to initialize Trustcase client
 */
public class TrustCaseCredentials {
    private final String jid;
    private final String password;
    private final String privateKey;
    private final String publicKey;

    public TrustCaseCredentials(String jid, String password, KeyPair keyPair) {
        this(jid, password, keyPair.getPrivateKey(), keyPair.getPublicKey());
    }

    public TrustCaseCredentials(String jid, String password, String privateKey, String publicKey) {
        super();
        this.jid = ensureNotNull(jid, "jid");
        this.password = ensureNotNull(password, "password");
        this.privateKey = ensureNotNull(privateKey, "privateKey");
        this.publicKey = ensureNotNull(publicKey, "publicKey");
    }

    private String ensureNotNull(String value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " is null");
        }
        return value;
    }

    /**
     * @return the jid
     */
    public String getJid() {
        return jid;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the privateKey
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * @return the publicKey
     */
    public String getPublicKey() {
        return publicKey;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((jid == null) ? 0 : jid.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((privateKey == null) ? 0 : privateKey.hashCode());
        result = prime * result + ((publicKey == null) ? 0 : publicKey.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TrustCaseCredentials other = (TrustCaseCredentials) obj;
        if (jid == null) {
            if (other.jid != null)
                return false;
        } else if (!jid.equals(other.jid))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (privateKey == null) {
            if (other.privateKey != null)
                return false;
        } else if (!privateKey.equals(other.privateKey))
            return false;
        if (publicKey == null) {
            if (other.publicKey != null)
                return false;
        } else if (!publicKey.equals(other.publicKey))
            return false;
        return true;
    }

}
