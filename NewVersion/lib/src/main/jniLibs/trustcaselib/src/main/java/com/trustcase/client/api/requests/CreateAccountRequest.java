package com.trustcase.client.api.requests;

import com.trustcase.client.api.enums.DeviceType;

/**
 * Model for request to create account.
 *
 * Created by audrius on 15.10.14.
 */
public class CreateAccountRequest implements Request {
	public String code;
	public String public_key;
	public String push_id;
	public DeviceType device_type;

	public CreateAccountRequest() {
	}

	public CreateAccountRequest(String code, String publicKey, String pushId,
			DeviceType deviceType) {
		super();
		this.code = code;
		this.public_key = publicKey;
		this.push_id = pushId;
		this.device_type = deviceType;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the public_key
	 */
	public String getPublicKey() {
		return public_key;
	}

	/**
	 * @return the push_id
	 */
	public String getPushId() {
		return push_id;
	}

	/**
	 * @return the device_type
	 */
	public DeviceType getDeviceType() {
		return device_type;
	}

	@Override
	public String toString() {
		return "CreateAccountRequest{" + "code='" + code + '\''
				+ ", public_key='" + public_key + '\'' + ", push_id='"
				+ push_id + '\'' + ", device_type='" + device_type + '\'' + '}';
	}
}
