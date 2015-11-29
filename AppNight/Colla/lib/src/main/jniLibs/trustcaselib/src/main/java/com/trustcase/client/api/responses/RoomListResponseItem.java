package com.trustcase.client.api.responses;

import java.util.Date;

/**
 * One item in response of "get room list" call
 */
public class RoomListResponseItem {
    public String room_id;
    public Date metadata_last_updated;
    public Date participants_last_updated;
    public boolean is_open;
    public Date last_message_date;
    public String last_message_by;

    public RoomListResponseItem() {
    }

    public String getId() {
        return room_id;
    }

    public Date getMetadataLastUpdated() {
        return metadata_last_updated;
    }

    public Date getParticipantsLastUpdated() {
        return participants_last_updated;
    }

    public boolean isOpen() {
        return is_open;
    }

    public Date getLastMessageDate() {
        return last_message_date;
    }

    public String getLastMessageBy() {
        return last_message_by;
    }
}
