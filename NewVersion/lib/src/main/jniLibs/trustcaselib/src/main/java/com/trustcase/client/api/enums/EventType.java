package com.trustcase.client.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Supported event types
 */
public enum EventType {
    @SerializedName("open_room")
    OPEN_ROOM,
    @SerializedName("close_room")
    CLOSE_ROOM,
    @SerializedName("invite")
    INVITE,
    @SerializedName("kick")
    KICK,
    @SerializedName("leave")
    LEAVE,
    @SerializedName("metadata_update")
    METADATA_UPDATE,
    @SerializedName("participants_update")
    PARTICIPANTS_UPDATE;

    public static EventType fromString(String value) {
        return EventType.valueOf(value.toUpperCase());
    }

}
