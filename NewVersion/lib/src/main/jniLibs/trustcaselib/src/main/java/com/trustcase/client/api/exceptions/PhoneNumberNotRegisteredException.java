package com.trustcase.client.api.exceptions;

import com.trustcase.client.TrustCaseClient;

/**
 * Exception indicating that phone number passed to TrustCase client is not a registered TrustCase user (e.g. see
 * posting a private message:
 * {@link TrustCaseClient#postPrivateMessage(String, String, com.trustcase.client.api.messages.Message)}.
 * 
 * @see TrustCaseClient#postPrivateMessage(String, String, com.trustcase.client.api.messages.Message)
 */
public class PhoneNumberNotRegisteredException extends TrustCaseClientException {
    private static final long serialVersionUID = 1L;

    public PhoneNumberNotRegisteredException() {
        super();
    }
}
