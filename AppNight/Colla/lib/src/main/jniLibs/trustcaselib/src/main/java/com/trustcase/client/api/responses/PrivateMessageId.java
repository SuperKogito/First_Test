package com.trustcase.client.api.responses;

/**
 * Response for "post private message" call
 * Encapsulates room ID and message ID in one wrapper object
 */
public class PrivateMessageId {
    public String message_id;
    public String room_id;

    public PrivateMessageId(String room_id, String message_id) {
        this.message_id = message_id;
        this.room_id = room_id;
    }

    /**
     * @return the message ID
     */
    public String getMessageId() {
        return message_id;
    }

    /**
     * @return the room ID
     */
    public String getRoomId() {
        return room_id;
    }
}