package com.trustcase.client.api.messages;

import com.trustcase.client.api.enums.EventType;
import com.trustcase.client.api.enums.MessageType;
import com.trustcase.client.api.responses.Identity;

import java.util.ArrayList;
import java.util.List;

/**
 * Message contained in MessageEnvelope when type of envelope is EnvelopeType.EVENT
 */
public class EventMessage implements Message {
    private static final long serialVersionUID = 1L;
    public EventType type;
    public List<Identity> participants;
    public String metadata;

    public EventMessage() {
        participants = new ArrayList<>();
    }

    @Override
    public MessageType getType() {
        return MessageType.EVENT;
    }
    
    public EventType getEventType() {
        return type;
    }
    
    /**
     * @return the participants
     */
    public List<Identity> getParticipants() {
        return participants;
    }
    
    /**
     * @return the metadata
     */
    public String getMetadata() {
        return metadata;
    }

    @Override
    public String asText() {
        return type.toString();
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "type=" + type +
                ", participants=" + participants +
                ", metadata='" + metadata + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
        result = prime * result + ((participants == null) ? 0 : participants.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EventMessage other = (EventMessage) obj;
        if (metadata == null) {
            if (other.metadata != null)
                return false;
        } else if (!metadata.equals(other.metadata))
            return false;
        if (participants == null) {
            if (other.participants != null)
                return false;
        } else if (!participants.equals(other.participants))
            return false;
        if (type != other.type)
            return false;
        return true;
    }
    
    
}
