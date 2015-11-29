package com.trustcase.client.api.requests;

import java.util.ArrayList;
import java.util.List;

/**
 * Request to server to open new room
 */
public class OpenRoomRequest implements Request {
    public List<String> participants;
    public String data;

    public OpenRoomRequest() {
        participants = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "OpenRoomRequest {" +
              "participants=" + participants +
              '}';
    }
}
