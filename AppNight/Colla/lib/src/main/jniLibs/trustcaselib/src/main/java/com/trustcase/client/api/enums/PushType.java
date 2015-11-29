package com.trustcase.client.api.enums;

/**
 * Supported push notification types sent to mobile devices
 */
public enum PushType {
    UNKNOWN, MESSAGE, NEW_ROOM, CHANGED_ROOM, INVITE, KICK, CLOSED_ROOM, LOCATION_REQUEST;

    public static PushType fromString(String value) {
        try {
            return PushType.valueOf(value.toUpperCase());
        } catch (Exception e) {
            return UNKNOWN;
        }
    }
}
