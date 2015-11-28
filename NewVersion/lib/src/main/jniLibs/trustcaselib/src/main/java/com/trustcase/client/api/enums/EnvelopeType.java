package com.trustcase.client.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Types of items in message stream: can be either messages or events
 */
public enum EnvelopeType {
    @SerializedName("message")
    MESSAGE,
    @SerializedName("event")
    EVENT;

    public static EnvelopeType fromString(String value) {
        return EnvelopeType.valueOf(value.toUpperCase());
    }
}