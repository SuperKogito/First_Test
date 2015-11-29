package com.trustcase.client.api.exceptions;

import com.trustcase.client.TrustCaseClient;

/**
 * Base exception class for any errors that occur while attempting to use a
 * {@link TrustCaseClient} to make service calls to the TrustCase Web Service.
 * Error responses from services will be handled as
 * {@link TrustCaseServiceException}s.<br>
 * This class is primarily for errors that occur when unable to get a response
 * from a service ({@link NetworkException}), when the client is unable to
 * understand a response from a service ({@link JSONException}), or another
 * failure occurs on the client side (e.g. an encryption error indicated by
 * {@link EncryptionException}). For example, if a caller tries to use a client
 * to make a service call, but no network connection is present, a
 * {@link NetworkException} will be thrown to indicate that the client wasn't
 * able to successfully make the service call, and no information from the
 * service is available.<br>
 * 
 * @author Gunther Klein
 */
public class TrustCaseClientException extends Exception {
	private static final long serialVersionUID = 1L;

	public TrustCaseClientException() {
		super();
	}

	public TrustCaseClientException(String message) {
		super(message);
	}

	public TrustCaseClientException(Throwable cause) {
		super(cause);
	}

	public TrustCaseClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public TrustCaseClientException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
