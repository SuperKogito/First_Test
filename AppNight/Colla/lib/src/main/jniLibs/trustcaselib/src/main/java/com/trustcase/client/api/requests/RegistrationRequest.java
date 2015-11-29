package com.trustcase.client.api.requests;

/**
 * Registration request.
 *
 * Created by audrius on 15.10.14.
 */
public class RegistrationRequest implements Request {
    public String phone;
    public String language;

    public RegistrationRequest(String phoneNumber, String language) {
        this.phone = phoneNumber;
        this.language = language;
    }

    @Override
    public String toString() {
        return "Registration{" + "phone='" + phone + ", language=" + language + '}';
    }
}
