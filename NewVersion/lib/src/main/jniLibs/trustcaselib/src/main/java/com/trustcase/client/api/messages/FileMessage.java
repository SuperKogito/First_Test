package com.trustcase.client.api.messages;


import com.trustcase.client.api.enums.MessageType;

/**
 * Message representing file attachments
 */
public class FileMessage implements Message {
	private static final long serialVersionUID = 1L;
	
	public String file_id;
    public String download_link;
    public String mime_type;
    public long size;
    public String filename;
    public String description;
    public String key;
    public boolean assigned_to_task;

    public FileMessage() {
    }

    @Override
    public MessageType getType() {
        return MessageType.FILE;
    }

    @Override
    public String asText() {
        return description;
    }

    @Override
    public String toString() {
        return String.format(
              "FileMessage{file_id='%s', download_link='%s', mime_type='%s', size=%d, filename='%s', description='%s', key='%s', assignedToTask=%s}",
              file_id,
              download_link,
              mime_type,
              size,
              filename,
              description,
              key,
              assigned_to_task);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (assigned_to_task ? 1231 : 1237);
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((download_link == null) ? 0 : download_link.hashCode());
        result = prime * result + ((file_id == null) ? 0 : file_id.hashCode());
        result = prime * result + ((filename == null) ? 0 : filename.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((mime_type == null) ? 0 : mime_type.hashCode());
        result = prime * result + (int) (size ^ (size >>> 32));
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
        FileMessage other = (FileMessage) obj;
        if (assigned_to_task != other.assigned_to_task)
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (download_link == null) {
            if (other.download_link != null)
                return false;
        } else if (!download_link.equals(other.download_link))
            return false;
        if (file_id == null) {
            if (other.file_id != null)
                return false;
        } else if (!file_id.equals(other.file_id))
            return false;
        if (filename == null) {
            if (other.filename != null)
                return false;
        } else if (!filename.equals(other.filename))
            return false;
        if (key == null) {
            if (other.key != null)
                return false;
        } else if (!key.equals(other.key))
            return false;
        if (mime_type == null) {
            if (other.mime_type != null)
                return false;
        } else if (!mime_type.equals(other.mime_type))
            return false;
        if (size != other.size)
            return false;
        return true;
    }
    
}