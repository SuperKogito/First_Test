package com.trustcase.client.api.messages;

import com.trustcase.client.api.enums.MessageType;
import com.trustcase.client.api.enums.TaskResponseType;

import java.util.Date;
import java.util.List;

/**
 * Message representing tasks
 */
public class TaskMessage implements Message {
	private static final long serialVersionUID = 1L;
	
	public String title;
    public TaskResponseType response_type;
    public List<String> options;
    // note: assigning files to task is optional
    public List<FileMessage> assigned_files;
    public Date deadline;
    public String recipient;
    public int digits;

    public TaskMessage(String title) {
        this.title = title;
    }

    @Override
    public MessageType getType() {
        return MessageType.TASK;
    }

    @Override
    public String asText() { return title; }

    @Override
    public String toString() {
        return "TaskMessage{" +
              "title='" + title + '\'' +
              ", response_type=" + response_type +
              ", options=" + options +
              ", assigned_files=" + assigned_files +
              ", deadline=" + deadline +
              ", recipient='" + recipient + '\'' +
              ", digits=" + digits +
              '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskMessage that = (TaskMessage) o;

        if (digits != that.digits) return false;
        if (deadline != null ? !deadline.equals(that.deadline) : that.deadline != null) return false;
        if (options != null ? !options.equals(that.options) : that.options != null) return false;
        if (assigned_files != null ? !assigned_files.equals(that.assigned_files) : that.assigned_files != null) return false;
        if (recipient != null ? !recipient.equals(that.recipient) : that.recipient != null) return false;
        if (response_type != that.response_type) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (response_type != null ? response_type.hashCode() : 0);
        result = 31 * result + (options != null ? options.hashCode() : 0);
        result = 31 * result + (assigned_files != null ? assigned_files.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + (recipient != null ? recipient.hashCode() : 0);
        result = 31 * result + digits;
        return result;
    }
}