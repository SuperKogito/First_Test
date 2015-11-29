package com.trustcase.client.api.responses;

/**
 * Server response for posting a message (403 Post message).
 * 403 is not an error identifier here :)
 */
public class MessageId {
    /**
     * GUID assigned to the message by the server
     */
    public String message_id;
    
    /**
     * @return the message d
     */
    public String getMessageId() {
        return message_id;
    }
}