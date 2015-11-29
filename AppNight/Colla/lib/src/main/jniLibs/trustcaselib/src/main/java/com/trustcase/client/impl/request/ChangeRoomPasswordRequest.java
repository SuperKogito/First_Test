package com.trustcase.client.impl.request;


/**
 * Requests changing room password on server.
 */
public class ChangeRoomPasswordRequest extends PasswordRequest {
    String new_password;
    /**
     * @param oldPassword - user must have administrator privileges in order to invite people to the room
     * @param newPassword - new password
     */
    public ChangeRoomPasswordRequest(String oldPassword, String newPassword) {
        super(oldPassword);
        this.new_password = newPassword;
    }
}