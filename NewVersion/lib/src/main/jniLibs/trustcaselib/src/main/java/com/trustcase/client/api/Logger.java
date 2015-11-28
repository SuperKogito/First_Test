package com.trustcase.client.api;

import com.trustcase.client.TrustCaseClient;

/**
 * When creating a {@link TrustCaseClient} can be specified to control logging (e.g. to console, file, etc) of the rest
 * calls in the API.
 * 
 * @see LogLevel
 * @see com.trustcase.client.TrustCase
 * @author Gunther Klein
 */
public interface Logger {

    void log(String message);
}
