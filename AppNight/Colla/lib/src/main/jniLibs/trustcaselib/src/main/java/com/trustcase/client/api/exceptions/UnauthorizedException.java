package com.trustcase.client.api.exceptions;

import com.trustcase.client.api.responses.ErrorResponse;

/**
 * {@link TrustCaseServiceException} indicating that TrustCase server didn't accept login data: authentication header
 * missing/invalid (HTTP status 401)
 * 
 * @author Audrius Stonkus
 * @author Gunther Klein
 */
public class UnauthorizedException extends TrustCaseServiceException {
    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String message, Throwable cause, ErrorResponse errorResponse) {
        super(message, cause, 401, errorResponse);
    }
}
