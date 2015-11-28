package com.trustcase.client.api.exceptions;

import com.trustcase.client.api.responses.ErrorResponse;

/**
 * Extension of TrustCaseClientException that represents an error response returned by the TrustCase web service.
 * Receiving an exception of this type indicates that the caller's request was correctly transmitted to the service, but
 * for some reason, the service was not able to process it, and returned an error response instead.<br>
 * The status code field provides a hint about the concrete reason of the error. Note, that there exist dedicated
 * subclasses for the most common status codes that may be returned from TrustCase web services (e.g.
 * {@link BadRequestException}, {@link NotFoundException}, {@link UnauthorizedException}, etc.)
 * 
 * @author Gunther Klein
 */
public class TrustCaseServiceException extends TrustCaseClientException {
    private static final long serialVersionUID = 1L;
    private final int statusCode;
    private final ErrorResponse errorResponse;

    public TrustCaseServiceException(int statusCode, ErrorResponse errorResponse) {
        this.statusCode = statusCode;
        this.errorResponse = errorResponse;
    }

    public TrustCaseServiceException(String message, int statusCode, ErrorResponse errorResponse) {
        super(message);
        this.statusCode = statusCode;
        this.errorResponse = errorResponse;
    }

    public TrustCaseServiceException(Throwable cause, int statusCode, ErrorResponse errorResponse) {
        super(cause);
        this.statusCode = statusCode;
        this.errorResponse = errorResponse;
    }

    public TrustCaseServiceException(String message, Throwable cause, int statusCode, ErrorResponse errorResponse) {
        super(message, cause);
        this.statusCode = statusCode;
        this.errorResponse = errorResponse;
    }

    /**
     * Returns the HTTP status code that was returned with this service exception.
     * 
     * @return the statusCode the HTTP status code that was returned with this service exception.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * @return the errorResponse
     */
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

}
