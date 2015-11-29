package com.trustcase.client.api.messages;

import com.google.gson.annotations.SerializedName;
import com.trustcase.client.api.enums.EnvelopeType;
import com.trustcase.client.api.exceptions.TrustCaseClientException;

import java.util.Date;

/**
 * Single item in message stream, which contains either encrypted message (message & messageWrapper, when type==MESSAGE),
 * or event (eventMessage, when type==EVENT), or error (if type couldn't be determined or decryption error occurred)
 *
 * Created by audrius on 19.10.14.
 */
public class MessageEnvelope {
    public String message_id;
    public String room_id;
    public String node;
    public Date created;
    public String creator;
    public EnvelopeType type;
    /**
     * Encrypted message data, present if type == MESSAGE
     */
    @SerializedName(value = "message")
    public MessageWrapper messageWrapper;
    /**
     * Event data, present if type == EVENT
     */
    @SerializedName(value = "event")
    public EventMessage eventMessage;
    /**
     * Decrypted message, present if type == MESSAGE and decryption was successful. Not in initial JSON.
     */
    @SerializedName(value = "decryptedMessage")
    public Message message;
    /**
     * Error which happened while decrypting/parsing enclosed message.
     */
    public TrustCaseClientException error;

    public MessageEnvelope() {
    }

    public boolean hasValidMessage() {
        return type == EnvelopeType.MESSAGE && message != null;
    }

    public boolean hasValidEvent() {
        return type == EnvelopeType.EVENT && eventMessage != null;
    }

    /**
     * @return the message_id
     */
    public String getMessageId() {
        return message_id;
    }

    /**
     * @return the room_id
     */
    public String getRoomId() {
        return room_id;
    }

    /**
     * @return the created
     */
    public Date getCreatedDate() {
        return created;
    }

    /**
     * @return the message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * @return the eventMessage
     */
    public EventMessage getEventMessage() {
        return eventMessage;
    }

    /**
     * @return the error
     */
    public TrustCaseClientException getError() {
        return error;
    }

    /**
     * @return the node
     */
    public String getNode() {
        return node;
    }

    /**
     * @return the type
     */
    public EnvelopeType getType() {
        return type;
    }

    /**
     * @return the creator
     */
    public String getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return "MessageEnvelope{" +
                "message_id='" + message_id + '\'' +
                ", room_id='" + room_id + '\'' +
                ", node='" + node + '\'' +
                ", created=" + created +
                ", creator='" + creator + '\'' +
                ", type=" + type +
                ", messageWrapper=" + messageWrapper +
                ", eventWrapper=" + eventMessage +
                ", message=" + message +
                '}';
    }
}
