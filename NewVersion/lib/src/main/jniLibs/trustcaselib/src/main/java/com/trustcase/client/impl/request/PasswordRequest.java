package com.trustcase.client.impl.request;

import com.trustcase.client.api.requests.Request;

/**
 * Base class for all request objects that require authenticating user with a password
 */
public abstract class PasswordRequest implements Request {
    public final String password;

    protected PasswordRequest(String password) {
        this.password = password;
    }
}