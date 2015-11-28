package com.trustcase.client.api.responses;

/**
 * Response from server that account was created.
 *
 * Created by audrius on 15.10.14.
 */
public class CreateAccountResponse {
    public String jid;
    public String password;

    public CreateAccountResponse(String jid, String password) {
        this.jid = jid;
        this.password = password;
    }

    /**
     * @return the jid
     */
    public String getJid() {
        return jid;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "CreateAccountResponse{" + "jid='" + jid + '\'' + ", password='" + password + '\'' + '}';
    }
}
