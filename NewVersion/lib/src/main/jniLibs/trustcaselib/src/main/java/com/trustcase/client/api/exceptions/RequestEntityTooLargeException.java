package com.trustcase.client.api.exceptions;

import com.trustcase.client.api.responses.ErrorResponse;

/**
 * Exception indicating that the requests entity was too large (e.g. when uploading a too big file) (HTTP status 413)
 * 
 * @author Gunther Klein
 */
public class RequestEntityTooLargeException extends TrustCaseServiceException {
    private static final long serialVersionUID = 1L;

    public RequestEntityTooLargeException(String message, Throwable cause, ErrorResponse errorResponse) {
        super(message, cause, 413, errorResponse);
    }
}
