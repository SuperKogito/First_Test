package com.trustcase.client.api.exceptions;

import com.trustcase.client.api.responses.ErrorResponse;

/**
 * Exception indicating that TrustCase server is in maintenance or temporary unavailable (HTTP status 503)
 */
public class ServiceTemporaryUnavailableException extends TrustCaseServiceException {
    private static final long serialVersionUID = 1L;

    public static final String RETRY_AFTER_HEADER = "Retry-After";
    public static final String MAINTENANCE_ERROR_CODE = "01001";

    private int retryAfter; // In seconds

    public ServiceTemporaryUnavailableException(String message, Throwable cause,
            ErrorResponse errorResponse, int retryAfter) {
        super(message, cause, 503, errorResponse);
        this.retryAfter = retryAfter;
    }

    public int getRetryAfter() {
        return retryAfter;
    }

    /**
     * Errors caused by planned maintenance work will deliver valid JSON error pages, where unexpected downtime will
     * not.
     */
    public boolean isMaintenanceWork() {
        return getErrorResponse() != null && MAINTENANCE_ERROR_CODE.equals(getErrorResponse().getErrorCode());
    }
}
