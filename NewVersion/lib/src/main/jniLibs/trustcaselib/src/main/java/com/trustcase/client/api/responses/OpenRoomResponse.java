package com.trustcase.client.api.responses;

import java.util.ArrayList;
import java.util.List;

/**
 * Server response that the room has been created.
 *
 * Created by audrius on 15.10.14.
 */
public class OpenRoomResponse {
    
    /**
     * Room id
     */
    public String room_id;
    
    /**
     * Room password for admins.
     */
    public String password;
    
    /**
     * Participants of the room.
     */
    public List<Identity> participants;

    public OpenRoomResponse() {
        participants = new ArrayList<>();
    }

    /**
     * @return the room_id
     */
    public String getRoomId() {
        return room_id;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the participants
     */
    public List<Identity> getParticipants() {
        return participants;
    }

    @Override
    public String toString() {
        return "OpenRoomResponse{" + "room_id='" + room_id + '\'' + ", password='" + password + '\''
                + ", participants=" + participants + '}';
    }
}
