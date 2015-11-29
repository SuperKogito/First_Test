package com.trustcase.client.api.exceptions;

import com.trustcase.client.api.responses.ErrorResponse;

/**
 * Exception indicating that TrustCase server didn't accept data sent to it (HTTP status 400)
 * 
 * @author Audrius Stonkus
 * @author Gunther Klein
 */
public class BadRequestException extends TrustCaseServiceException {
    private static final long serialVersionUID = 1L;

    public BadRequestException(String message, Throwable cause, ErrorResponse errorResponse) {
        super(message, cause, 400, errorResponse);
    }
}
