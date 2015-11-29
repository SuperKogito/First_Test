package com.trustcase.client.api.responses;

import com.trustcase.client.api.enums.TrustCaseRole;

import java.io.Serializable;

/**
 * Information about one TrustCase account - used in multiple requests/responses.
 */
public class Identity implements Serializable {
	private static final long serialVersionUID = 1L;
	public String jid;
    public String public_key;
    public TrustCaseRole role;
    public boolean is_active;

    public Identity() {
		is_active = true;
    }

    public Identity(String jid, String public_key, TrustCaseRole role) {
		this();
        this.jid = jid;
        this.public_key = public_key;
        this.role = role;
    }

    public Identity(String jid, String public_key) {
        this(jid, public_key, TrustCaseRole.MOBILE_USER, true);
    }

	public Identity(String jid, String public_key, TrustCaseRole role, boolean active) {
		this(jid, public_key, role);
		this.is_active = active;
	}

    /**
     * @return the jid
     */
    public String getJid() {
        return jid;
    }
    
    /**
     * @return the public_key
     */
    public String getPublicKey() {
        return public_key;
    }
    
    /**
     * @return the role
     */
    public TrustCaseRole getRole() {
        return role;
    }

	/**
	 * @return if this user is active. Currently, active==false means that user has re-registered with same phone
	 * number and became new JID
	 */
	public boolean isActive() {
		return is_active;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Identity)) {
            return false;
        }
        Identity identity = (Identity) o;
        if (jid != null ? !jid.equals(identity.jid) : identity.jid != null) {
            return false;
        }
        if (public_key != null ? !public_key.equals(identity.public_key) :
                identity.public_key != null) {
            return false;
        }
        if (role != identity.role) {
            return false;
        }
		if (is_active != identity.is_active) {
			return false;
		}
        return true;
    }

    @Override
    public int hashCode() {
        int result = jid.hashCode();
        result = 31 * result + public_key.hashCode();
        result = 31 * result + role.hashCode();
		result = 31 * result + (is_active ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Identity{" +
                "jid='" + jid + '\'' +
                ", public_key='" + public_key + '\'' +
                ", role='" + role + '\'' +
				", is_active='" + is_active + '\'' +
                '}';
    }
}
