package com.trustcase.client.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Possible device types for new account registration
 */
public enum DeviceType {

	/**
	 * Represents and iOS device
	 */
	@SerializedName("iOS")
	IOS,
	/**
	 * Represents and Android device
	 */
	@SerializedName("Android")
	ANDROID,
	/**
	 * Represents a Windows device
	 */
	@SerializedName("Windows")
	WINDOWS,
	/**
	 * Represents a third-party client
	 */
	@SerializedName("System")
	SYSTEM;
}
