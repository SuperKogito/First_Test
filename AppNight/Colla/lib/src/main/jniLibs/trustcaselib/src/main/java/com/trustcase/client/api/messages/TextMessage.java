package com.trustcase.client.api.messages;

import com.trustcase.client.api.enums.MessageType;

/**
 * Normal chat message
 *
 * Created by audrius on 20.10.14.
 */
public class TextMessage implements Message {
    private static final long serialVersionUID = 1L;
    public String text;

    public TextMessage(String text) {
        this.text = text;
    }

    @Override
    public MessageType getType() {
        return MessageType.TEXT;
    }

    public String getText() {
        return text;
    }

    @Override
    public String asText() {
        return text;
    }

    @Override
    public String toString() {
        return "TextMessage{" + "text='" + text + '\'' + '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((text == null) ? 0 : text.hashCode());
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
        TextMessage other = (TextMessage) obj;
        if (text == null) {
            if (other.text != null)
                return false;
        } else if (!text.equals(other.text))
            return false;
        return true;
    }

}