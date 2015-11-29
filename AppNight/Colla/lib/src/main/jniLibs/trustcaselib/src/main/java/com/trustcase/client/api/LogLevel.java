package com.trustcase.client.api;

import com.trustcase.client.TrustCase;
import com.trustcase.client.TrustCaseClient;

/**
 * Controls the level of logging in the TrustCase Client API. Can be specified when creating a {@link TrustCaseClient}
 * via {@link TrustCase}.
 * 
 * @see TrustCase
 * @author Gunther Klein
 */
public enum LogLevel {
    /**
     * Log level OFF: logging is turned off (default). This should be used in production code!
     */
    OFF,
    /**
     * Log level BASIC: only basic stuff is logged (e.g. request method, url, response status codes)
     */
    BASIC,
    /**
     * Log level EXTENDED: log basic information along with request and response objects
     */
    EXTENDED,
    /**
     * Log level FULL: Most verbose logging (e.g. Log the headers, body, and metadata for both requests and responses).<br>
     * Note, that this should be avoided in production environment. This requires that the entire request and response
     * body be buffered in memory!
     */
    FULL;
}
