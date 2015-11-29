package com.trustcase.client.api.messages;

import com.trustcase.client.api.enums.MessageType;

/**
 * Message used to send encryption key for your profile to other users
 */
public class ProfileKeyMessage implements Message {
    private static final long serialVersionUID = 1L;

    public String key;

    public ProfileKeyMessage(String key) {
        this.key = key;
    }

    @Override
    public MessageType getType() {
        return MessageType.PROFILE_KEY;
    }

    @Override
    public String asText() {
        return key;
    }

    @Override
    public String toString() {
        return "ProfileKeyMessage {" + "key='" + key + '\'' + '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProfileKeyMessage other = (ProfileKeyMessage) obj;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        return true;
    }

}