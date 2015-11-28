package com.trustcase.client.api.messages;

/**
 * Participant profile info (name and picture)
 * Created by audrius on 16.09.15.
 */
public class Profile {
	private String name;
	private FileMessage pictureMessage;

	public Profile(String name, FileMessage picture) {
		this.name = name;
		this.pictureMessage = picture;
	}

	public String getName() {
		return name;
	}

	public FileMessage getPictureMessage() {
		return pictureMessage;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Profile profile = (Profile) o;

		if (name != null ? !name.equals(profile.name) : profile.name != null) return false;
		return !(pictureMessage != null ? !pictureMessage.equals(profile.pictureMessage) : profile.pictureMessage != null);
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (pictureMessage != null ? pictureMessage.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Profile{" +
				"name='" + name + '\'' +
				", pictureMessage=" + pictureMessage +
				'}';
	}
}
