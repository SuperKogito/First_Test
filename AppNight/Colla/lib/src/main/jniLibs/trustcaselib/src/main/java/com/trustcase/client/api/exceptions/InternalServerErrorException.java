package com.trustcase.client.api.exceptions;

import com.trustcase.client.api.responses.ErrorResponse;

/**
 * Exception indicating internal problem in TrustCase server (HTTP status 500)
 * 
 * @author Audrius Stonkus
 * @author Gunther Klein
 */
public class InternalServerErrorException extends TrustCaseServiceException {
    private static final long serialVersionUID = 1L;

    public InternalServerErrorException(String message, Throwable cause, ErrorResponse errorResponse) {
        super(message, cause, 500, errorResponse);
    }
}
