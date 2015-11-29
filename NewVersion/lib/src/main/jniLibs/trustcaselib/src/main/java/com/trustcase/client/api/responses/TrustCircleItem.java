package com.trustcase.client.api.responses;


import com.trustcase.client.api.enums.TrustMode;

/**
 * Item in request to "add to trust circle" or in response to "query trust circle" calls
 * Created by audrius on 08.09.15.
 */
public class TrustCircleItem {
	public String jid;
	public TrustMode trustMode;
	public String profile;
	public String public_key;

	public TrustCircleItem() {
	}

	public TrustCircleItem(String jid, TrustMode trustMode, String profile) {
		this.jid = jid;
		this.trustMode = trustMode;
		this.profile = profile;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TrustCircleItem that = (TrustCircleItem) o;

		if (jid != null ? !jid.equals(that.jid) : that.jid != null) return false;
		if (trustMode != that.trustMode) return false;
		return !(profile != null ? !profile.equals(that.profile) : that.profile != null);

	}

	@Override
	public int hashCode() {
		int result = jid != null ? jid.hashCode() : 0;
		result = 31 * result + (trustMode != null ? trustMode.hashCode() : 0);
		result = 31 * result + (profile != null ? profile.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "TrustCircleItem{" +
				"jid='" + jid + '\'' +
				", trustMode=" + trustMode +
				", profile='" + profile + '\'' +
				'}';
	}
}
