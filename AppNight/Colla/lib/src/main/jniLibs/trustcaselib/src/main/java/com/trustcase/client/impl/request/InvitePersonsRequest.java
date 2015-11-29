package com.trustcase.client.impl.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Requests adding specified people (jids) from the room. (The id of the room is passed to the
 * adapter separately.)
 */
public class InvitePersonsRequest extends PasswordRequest {
    public final List<String> jids;

    public InvitePersonsRequest(String password, String jid) {
        this(password, Arrays.asList(jid));
    }

    /**
     * @param password - user must have administrator privileges in order to invite people to the room
     * @param jids     - identifiers of participants to be added to the room
     */
    public InvitePersonsRequest(String password, Collection<String> jids) {
        super(password);
        this.jids = new ArrayList<>(jids);
    }
}