package com.trustcase.client.api.enums;

import com.google.gson.annotations.SerializedName;

public enum RoomIcon {
    /* serializing icons in numeric form is required by API on the backend.
     * please make sure numbers in annotations are consistent with the ones passed in constructors -
     * nasty consequences of doing otherwise should be obvious. */

    /**
     * Represents lack of information - instead of a null
     */
    @SerializedName("-1")
    UNKNOWN(-1),
    /**
     * Only for presentation purposes
     * @deprecated - the word is that we don't need it anymore and it should be removed
     */
    @Deprecated
    @SerializedName("0")
    ADD_ICON(0),

    @SerializedName("1")
    BUILDING_1(1),
    @SerializedName("2")
    BUILDING_2(2),
    @SerializedName("3")
    BUILDING_3(3),
    @SerializedName("4")
    BUILDING_4(4),
    @SerializedName("5")
    BUILDING_5(5),
    @SerializedName("6")
    BUILDING_6(6),
    @SerializedName("7")
    BUILDING_7(7),
    
    @SerializedName("11")
    SYMBOL_1(11),
    @SerializedName("12")
    SYMBOL_2(12),
    @SerializedName("13")
    SYMBOL_3(13),
    @SerializedName("14")
    SYMBOL_4(14),
    @SerializedName("15")
    SYMBOL_5(15),
    @SerializedName("16")
    SYMBOL_6(16),
    @SerializedName("17")
    SYMBOL_7(17),

    @SerializedName("21")
    TRAFFIC_1(21),
    @SerializedName("22")
    TRAFFIC_2(22),
    @SerializedName("23")
    TRAFFIC_3(23),
    @SerializedName("24")
    TRAFFIC_4(24),
    @SerializedName("25")
    TRAFFIC_5(25),
    @SerializedName("26")
    TRAFFIC_6(26),
    @SerializedName("27")
    TRAFFIC_7(27),

    @SerializedName("31")
    DEFAULT_1(31),
    @SerializedName("32")
    DEFAULT_2(32);

    public static final RoomIcon[][] ICON_GROUPS = {
            {BUILDING_1, BUILDING_2, BUILDING_3, BUILDING_4, BUILDING_5, BUILDING_6, BUILDING_7},
            {TRAFFIC_1, TRAFFIC_2, TRAFFIC_3, TRAFFIC_4, TRAFFIC_5, TRAFFIC_6, TRAFFIC_7},
            {SYMBOL_1, SYMBOL_2, SYMBOL_3, SYMBOL_4, SYMBOL_5, SYMBOL_6, SYMBOL_7}
    };

    private final int value;

    RoomIcon(int value) {
        this.value = value;
    }

    public static RoomIcon fromInt(int value) {
        for (RoomIcon icon : RoomIcon.values()) {
            if (icon.toInt() == value) {
                return icon;
            }
        }
        return null;
    }

    public String getIdentifier() {
        return Integer.toString(value);
    }

    public int toInt() {
        return value;
    }
}