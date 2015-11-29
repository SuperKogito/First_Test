package com.trustcase.client.api.exceptions;

import com.trustcase.client.api.responses.ErrorResponse;

/**
 * Exception indicating usage of a deprecated API version (HTTP status 410). The protocol version is no longer supported
 * and has been permanently removed. If encountered in the context of a mobile application the user should be advised to
 * update the app.
 * 
 * @author Gunther Klein
 */
public class ResourceGoneException extends TrustCaseServiceException {
    private static final long serialVersionUID = 1L;

    private static final String DEPRECATED_API_ERROR_CODE = "01002";

    public ResourceGoneException(String message, Throwable cause, ErrorResponse errorResponse) {
        super(message, cause, 410, errorResponse);
    }

    /**
     * If valid error code is included it means this server API version id deprecated.
     */
    public boolean isApiDeprecated() {
        return getErrorResponse() != null && DEPRECATED_API_ERROR_CODE.equals(getErrorResponse().getErrorCode());
    }
}
