package com.trustcase.client.api.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Possible trust modes for trust circles
 * 
 */
public enum TrustMode {
	
	@SerializedName("push")
	PUSH,
	@SerializedName("sync")
	SYNC;
}
