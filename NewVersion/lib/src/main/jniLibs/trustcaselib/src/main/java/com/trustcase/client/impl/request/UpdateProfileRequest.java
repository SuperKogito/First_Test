package com.trustcase.client.impl.request;

/**
 * Model for request to update user's own profile.
 * <p/>
 * Created by audrius on 15.10.14.
 */
public class UpdateProfileRequest {
    public String profile;

    public UpdateProfileRequest() {
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
