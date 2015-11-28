package com.trustcase.client.api.responses;

/**
 * Model for local/remote contacts.
 */
public class Contact {
    public String jid;
    public String public_key;
    public String phone;
    public String name;
    public int contact_id;
    public String hash;

    public Contact(String jid, String publicKey, String phone) {
        this.jid = jid;
        this.public_key = publicKey;
        this.phone = phone;
    }

    /**
     * @return the jid
     */
    public String getJid() {
        return jid;
    }

    /**
     * @return the public key
     */
    public String getPublicKey() {
        return public_key;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the contact_id
     */
    public int getContactId() {
        return contact_id;
    }

    /**
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Contact)) {
            return false;
        }

        Contact contact = (Contact) o;

        if (contact_id != contact.contact_id) {
            return false;
        }
        if (hash != null ? !hash.equals(contact.hash) : contact.hash != null) {
            return false;
        }
        if (jid != null ? !jid.equals(contact.jid) : contact.jid != null) {
            return false;
        }
        if (name != null ? !name.equals(contact.name) : contact.name != null) {
            return false;
        }
        if (phone != null ? !phone.equals(contact.phone) : contact.phone != null) {
            return false;
        }
        if (public_key != null ? !public_key.equals(contact.public_key) : contact.public_key != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = jid != null ? jid.hashCode() : 0;
        result = 31 * result + (public_key != null ? public_key.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + contact_id;
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Contact{" + "name='" + name + '\'' + ", hash='" + hash + '\'' + ", public_key='" + public_key + '\''
                + ", contact_id=" + contact_id + ", phone='" + phone + '\'' + ", jid='" + jid + '\'' + '}';
    }
}