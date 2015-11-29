package com.trustcase.client.api.enums;

import com.google.gson.annotations.SerializedName;

/**
* Types of tasks - what answers do we want
*/
public enum TaskResponseType {
    @SerializedName("numeric")
    NUMERIC,
    @SerializedName("single_choice")
    SINGLE_CHOICE;

    public static final String KEY = TaskResponseType.class.getSimpleName();
    public static TaskResponseType fromString(String value) {
        return TaskResponseType.valueOf(value.toUpperCase());
    }
}
