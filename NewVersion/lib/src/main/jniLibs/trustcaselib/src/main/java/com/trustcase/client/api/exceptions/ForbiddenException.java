package com.trustcase.client.api.exceptions;

import com.trustcase.client.api.responses.ErrorResponse;

/**
 * Exception indicating that TrustCase server doesn't allow to perform this action (HTTP status 403)
 * 
 * @author Audrius Stonkus
 * @author Gunther Klein
 */
public class ForbiddenException extends TrustCaseServiceException {
    private static final long serialVersionUID = 1L;

    public ForbiddenException(String message, Throwable cause, ErrorResponse errorResponse) {
        super(message, cause, 403, errorResponse);
    }
}
