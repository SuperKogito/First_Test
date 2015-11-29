package com.trustcase.client.api.messages;

import com.trustcase.client.api.enums.MessageType;

/**
 * Message used to send new room password for all administrators
 */
public class RoomPasswordMessage implements Message {
    private static final long serialVersionUID = 1L;

    public String password;

    public RoomPasswordMessage(String password) {
        this.password = password;
    }

    @Override
    public MessageType getType() {
        return MessageType.ROOM_PASSWORD;
    }

    @Override
    public String asText() {
        return password;
    }

    @Override
    public String toString() {
        return "RoomPasswordMessage{" + "password='" + password + '\'' + '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
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
        RoomPasswordMessage other = (RoomPasswordMessage) obj;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        return true;
    }

}