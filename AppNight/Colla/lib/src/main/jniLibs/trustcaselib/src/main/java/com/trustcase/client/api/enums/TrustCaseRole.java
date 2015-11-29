package com.trustcase.client.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Roles of trustcase accounts
 */
public enum TrustCaseRole {
    @SerializedName("MobileUser")
    MOBILE_USER,
    @SerializedName("SystemUser")
    SYSTEM_USER,
    @SerializedName("SystemUserLocReqPerm")
    SYSTEM_USER_WITH_LOCATION_REQ_PERMISSION,
    @SerializedName("BlockedUser")
    BLOCKED_USER,
    @SerializedName("TrustBroker")
    TRUST_BROKER;

    public static TrustCaseRole fromString(String value) {
        return TrustCaseRole.valueOf(value.toUpperCase());
    }
}