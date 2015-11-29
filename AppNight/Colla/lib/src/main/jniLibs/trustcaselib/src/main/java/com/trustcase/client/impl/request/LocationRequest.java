package com.trustcase.client.impl.request;

import java.util.List;

/**
 * Data structure used when asking server to send out location requests
 */
public class LocationRequest {
    public List<String> recipients;
    public int timeout;

    public LocationRequest(List<String> recipients, int timeout) {
        this.recipients = recipients;
        this.timeout = timeout;
    }
}