package com.trustcase.client.api.responses;

/**
 * Response to add/remove trust circle items calls.
 * Created by audrius on 08.09.15.
 */
public class TrustCircleResponse {
	public int created;
	public int updated;
	public int deleted;

	public TrustCircleResponse() {
	}

	public TrustCircleResponse(int created, int updated, int deleted) {
		this.created = created;
		this.updated = updated;
		this.deleted = deleted;
	}
}
