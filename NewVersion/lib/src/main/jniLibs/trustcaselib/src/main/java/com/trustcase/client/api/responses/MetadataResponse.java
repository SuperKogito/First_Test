package com.trustcase.client.api.responses;

import java.util.Date;

/**
 * Response to "Get room metadata" call.
 *
 * Created by audrius on 15.10.14.
 */
public class MetadataResponse {

    public String room_id;
    public Date created_on;
    public String created_by;
    public Date metadata_last_updated;
    public Date participants_last_updated;
    public boolean is_open;
    public Date last_message_date;
    public String last_message_by;

    /**
     * RoomMetadataMessage object in the form of encrypted JSON
     * @see com.trustcase.client.api.messages.RoomMetadataMessage
     */
    public String data;

    public MetadataResponse() {
    }
    
    public String getRoomId() {
        return room_id;
    }

    public Date getCreatedOn() {
        return created_on;
    }

    public String getCreatedBy() {
        return created_by;
    }

    public Date getMetadataLastUpdated() {
        return metadata_last_updated;
    }

    public Date getParticipants_lastUpdated() {
        return participants_last_updated;
    }

    public boolean isOpen() {
        return is_open;
    }

    public String getData() {
        return data;
    }

    public Date getLastMessageDate() {
        return last_message_date;
    }

    public String getLastMessageBy() {
        return last_message_by;
    }

    @Override
    public String toString() {
        return "MetadataResponse{" +
                "room_id='" + room_id + '\'' +
                ", created_on=" + created_on +
                ", created_by='" + created_by + '\'' +
                ", metadata_last_updated=" + metadata_last_updated +
                ", participants_last_updated=" + participants_last_updated +
                ", is_open=" + is_open +
                ", last_message_date=" + last_message_date +
                ", last_message_by='" + last_message_by + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
