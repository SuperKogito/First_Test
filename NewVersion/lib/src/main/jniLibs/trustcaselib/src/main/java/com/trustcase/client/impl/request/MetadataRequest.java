package com.trustcase.client.impl.request;


/**
 * Request to update room metadata
 */
public class MetadataRequest extends PasswordRequest {
    public String data;

    public MetadataRequest(String password, String data) {
        super(password);
        this.data = data;
    }

    @Override
    public String toString() {
        return "MetadataRequest{" +
                "password='" + password + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}