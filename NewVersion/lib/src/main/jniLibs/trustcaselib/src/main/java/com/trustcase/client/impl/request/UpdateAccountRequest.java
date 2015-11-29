package com.trustcase.client.impl.request;

/**
 * Model for request to update registration info.
 *
 * Created by audrius on 15.10.14.
 */
public class UpdateAccountRequest {
    public String push_id;

    public UpdateAccountRequest(String push_id) {
        this.push_id = push_id;
    }
}
