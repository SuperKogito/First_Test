package com.trustcase.client.api.responses;

/**
 * One item in response of "retrieve profiles" call
 */
public class ProfileResponseItem {
    public String jid;
    public String public_key;
    public String profile;

    public ProfileResponseItem(String jid, String public_key, String profile) {
        this.jid = jid;
        this.public_key = public_key;
        this.profile = profile;
    }
}
