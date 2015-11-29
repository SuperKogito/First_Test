package com.trustcase.client.api.messages;

import com.trustcase.client.api.enums.MessageType;

import java.io.Serializable;

/**
 * Interface which all message classes will implement...
 *
 * @author audrius
 */
public interface Message extends Serializable {
    MessageType getType();
    String asText();
}
