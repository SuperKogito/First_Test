package com.trustcase.client.impl;

import com.trustcase.client.api.Logger;
import com.trustcase.client.api.exceptions.BadRequestException;
import com.trustcase.client.api.exceptions.ForbiddenException;
import com.trustcase.client.api.exceptions.InternalServerErrorException;
import com.trustcase.client.api.exceptions.JSONException;
import com.trustcase.client.api.exceptions.NetworkException;
import com.trustcase.client.api.exceptions.NotFoundException;
import com.trustcase.client.api.exceptions.RequestEntityTooLargeException;
import com.trustcase.client.api.exceptions.ResourceGoneException;
import com.trustcase.client.api.exceptions.ServiceTemporaryUnavailableException;
import com.trustcase.client.api.exceptions.TrustCaseClientException;
import com.trustcase.client.api.exceptions.TrustCaseServiceException;
import com.trustcase.client.api.exceptions.UnauthorizedException;
import com.trustcase.client.api.responses.ErrorResponse;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;

/**
 * Custom retrofit error handler to throw different exceptions depending on error type
 * 
 * @author Audrius Stonkus
 * @author Gunther Klein
 */
public class TrustCaseErrorHandler implements ErrorHandler {
    private Logger logger;

    public TrustCaseErrorHandler(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Throwable handleError(RetrofitError cause) {
        switch (cause.getKind()) {
            case NETWORK:
                return new NetworkException(cause);
            case CONVERSION:
                return new JSONException(cause);
            case HTTP: {
                ErrorResponse errorResponse = getErrorResponse(cause);
                switch (cause.getResponse().getStatus()) {
                    case 400:
                        return new BadRequestException(cause.getResponse().getReason(), cause, errorResponse);
                    case 401:
                        return new UnauthorizedException(cause.getResponse().getReason(), cause, errorResponse);
                    case 403:
                        return new ForbiddenException(cause.getResponse().getReason(), cause, errorResponse);
                    case 404:
                        return new NotFoundException(cause.getResponse().getReason(), cause, errorResponse);
                    case 410:
                        return new ResourceGoneException(cause.getResponse().getReason(), cause, errorResponse);
                    case 413:
                        return new RequestEntityTooLargeException(cause.getResponse().getReason(), cause, errorResponse);
                    case 500:
                        return new InternalServerErrorException(cause.getResponse().getReason(), cause, errorResponse);
                    case 503:
                        int retryAfter = getIntHeader(cause.getResponse(),
                                ServiceTemporaryUnavailableException.RETRY_AFTER_HEADER);
                        return new ServiceTemporaryUnavailableException(cause.getResponse().getReason(), cause,
                                errorResponse, retryAfter);
                    default:
                        return new TrustCaseServiceException(cause.getResponse().getReason(), cause, cause
                                .getResponse().getStatus(), errorResponse);
                }
            }
            case UNEXPECTED:
            default:
                return new TrustCaseClientException("Unexpected TrustCase error", cause);
        }
    }

    private void log(String message) {
        if (logger != null) {
            logger.log(message);
        }
    }

    private ErrorResponse getErrorResponse(RetrofitError cause) {
        try {
            return (ErrorResponse) cause.getBodyAs(ErrorResponse.class);
        } catch (Exception ex) {
            log("Parsing of error response failed, not a valid JSON: " + ex.getMessage());
            return null;
        }
    }

    private String getStringHeader(Response response, String headerName) {
        for (Header header : response.getHeaders()) {
            if (headerName.equals(header.getName())) {
                return header.getValue();
            }
        }
        return null;
    }

    private int getIntHeader(Response response, String headerName) {
        String value = getStringHeader(response, headerName);
        if (value == null) {
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            log(String.format("Parsing of header '%s' failed: %s", headerName, ex.getMessage()));
            return 0;
        }
    }
}
