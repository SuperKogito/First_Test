package com.trustcase.client.api.exceptions;

/**
 * TrustCaseClientException indicating network level error while communicating
 * with TrustCase server.<br>
 * For example, if a caller tries to use a client to make a service call, but no
 * network connection is present, a {@link NetworkException} will be thrown to
 * indicate that the client wasn't able to successfully make the service call,
 * and no information from the service is available
 * 
 * @author Audrius Stonkus
 * @author Gunther Klein
 */
public class NetworkException extends TrustCaseClientException {
	private static final long serialVersionUID = 1L;

	public NetworkException(Throwable cause) {
		super(cause);
	}

    public NetworkException(String message) {
        super(message);
    }
}
