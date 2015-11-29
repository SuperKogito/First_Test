package com.trustcase.client.api.messages;

import com.trustcase.client.api.enums.MessageType;

/**
 * Wrapper for encrypted message info, used internally to send encrypted messages to server.
 */
public class MessageWrapper {
    public MessageType type;
    public String data;

    public MessageWrapper() {
    }
    
    /**
     * @return the type
     */
    public MessageType getType() {
        return type;
    }
    
    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "MessageWrapper{" +
                "type=" + type +
                ", data='" + data + '\'' +
                '}';
    }
}
