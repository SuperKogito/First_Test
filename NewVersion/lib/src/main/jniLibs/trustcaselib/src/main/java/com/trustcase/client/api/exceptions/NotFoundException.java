package com.trustcase.client.api.exceptions;

import com.trustcase.client.api.responses.ErrorResponse;

/**
 * Exception indicating that the requested resource could not be found (HTTP status 404)
 * 
 * @author Gunther Klein
 */
public class NotFoundException extends TrustCaseServiceException {
    private static final long serialVersionUID = 1L;

    public NotFoundException(String message, Throwable cause, ErrorResponse errorResponse) {
        super(message, cause, 404, errorResponse);
    }
}
