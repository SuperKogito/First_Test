package com.trustcase.client.api.messages;

import java.util.ArrayList;
import java.util.List;

import com.trustcase.client.api.enums.MessageType;
import com.trustcase.client.api.enums.RoomIcon;

/**
 * Information about the room which is transferred to/from server encrypted.
 */
public class RoomMetadataMessage implements Message {

    private static final long serialVersionUID = 1L;

    private String name; // Room name
    private RoomIcon icon; // Room icon
    private List<String> administrators; // List of administrator JIDs
    private String room_key; // Encryption key in case room uses symmetric encryption, otherwise null

    public RoomMetadataMessage() {
        administrators = new ArrayList<>();
    }

    public RoomMetadataMessage(String name, RoomIcon icon, String roomKey, List<String> administrators) {
        this.name = name;
        this.icon = icon;
        this.administrators = administrators;
        this.room_key = roomKey;
    }

    public RoomMetadataMessage(String name, RoomIcon icon) {
        this(name, icon, null, new ArrayList<String>());
    }

    @Override
    public String asText() {
        return name;
    }

    public List<String> getAdministrators() {
        return administrators;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public MessageType getType() {
        return MessageType.METADATA;
    }

    public boolean isAdministrator(String jidOnServer) {
        return getAdministrators().contains(jidOnServer);
    }

    public void addAdministrator(String jid) {
        administrators.add(jid);
    }

    public void setIcon(RoomIcon icon) {
        this.icon = icon;
    }

    public RoomIcon getIcon() {
        return icon;
    }

    public void setAdministrators(List<String> administrators) {
        this.administrators = administrators;
    }

    public String getRoomKey() {
        return room_key;
    }

    public void setRoomKey(String key) {
        this.room_key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomMetadataMessage that = (RoomMetadataMessage) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (icon != that.icon) return false;
        if (!administrators.equals(that.administrators)) return false;
        return !(room_key != null ? !room_key.equals(that.room_key) : that.room_key != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + administrators.hashCode();
        result = 31 * result + (room_key != null ? room_key.hashCode() : 0);
        return result;
    }
}
