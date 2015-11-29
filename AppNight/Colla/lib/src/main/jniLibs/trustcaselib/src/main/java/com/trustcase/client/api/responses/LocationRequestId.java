package com.trustcase.client.api.responses;

/**
 * Server response for issuing location request (701)
 */
public class LocationRequestId {
    /**
     * Unique request ID assigned by server
     */
    public String request_id;
    
    /**
     * @return request id
     */
    public String getRequestId() {
        return request_id;
    }
}