package com.trustcase.client.api.responses;

import java.util.Date;

/**
 * Model for response to web login
 *
 */
public class WebLoginResponse {
	public Date valid_until;

	@Override
	public String toString() {
		return "WebLoginResponse{" +
				"valid_until=" + valid_until +
				'}';
	}
}
