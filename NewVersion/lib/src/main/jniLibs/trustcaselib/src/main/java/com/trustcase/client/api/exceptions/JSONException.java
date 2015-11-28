package com.trustcase.client.api.exceptions;

/**
 * Exception indicating that JSON data received from server couldn't be parsed
 */
public class JSONException extends TrustCaseClientException {
	private static final long serialVersionUID = 1L;

	public JSONException(Throwable cause) {
		super(cause);
	}
}
