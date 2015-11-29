package com.trustcase.client.api.responses;

/**
 * Model for request/response to web login
 *
 */
public class WebLogin {
	public String login_id;
	public String initial_data;

	public WebLogin(String loginId, String encryptedLoginData) {
		this.login_id = loginId;
		this.initial_data = encryptedLoginData;
	}

	@Override
	public String toString() {
		return "WebLoginRequest{" +
				"login_id='" + login_id + '\'' +
				", initial_data='" + initial_data + '\'' +
				'}';
	}
}
