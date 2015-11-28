package com.trustcase.client.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Supported message types
 */
public enum MessageType {
    @SerializedName("text")
    TEXT,
    @SerializedName("metadata")
    METADATA,
    @SerializedName("file")
    FILE,
    @SerializedName("password")
    ROOM_PASSWORD,
    @SerializedName("task")
    TASK,
    @SerializedName("taskresponse")
    TASK_RESPONSE,
    @SerializedName("event")
    EVENT,
    @SerializedName("profile")
    PROFILE_KEY,
    @SerializedName("location")
    LOCATION;

    public static MessageType fromString(String value) {
        return MessageType.valueOf(value.toUpperCase());
    }
}