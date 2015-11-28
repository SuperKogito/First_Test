package com.trustcase.client.impl.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Requests removing specified people (jids) from the room. (The id of the room is passed to the
 * adapter separately.)
 */
public class KickPersonsRequest extends PasswordRequest {
    public final List<String> jids;

    public KickPersonsRequest(String password, String jid) {
        this(password, Arrays.asList(jid));
    }

    /**
     * @param password - user must have administrator privileges in order to kick people from the room
     * @param jids     - identifiers of participants to be removed from the room
     */
    public KickPersonsRequest(String password, Collection<String> jids) {
        super(password);
        this.jids = new ArrayList<>(jids);
    }
}