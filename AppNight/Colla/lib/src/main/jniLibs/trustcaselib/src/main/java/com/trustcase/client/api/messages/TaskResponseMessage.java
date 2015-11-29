package com.trustcase.client.api.messages;

import com.trustcase.client.api.enums.MessageType;

/**
 * Message representing response to task message
 */
public class TaskResponseMessage implements Message {
	private static final long serialVersionUID = 1L;

	public String task_id;
    public int response;

    public TaskResponseMessage(String taskId, int response) {
        this.task_id = taskId;
        this.response = response;
    }

    @Override
    public MessageType getType() {
        return MessageType.TASK_RESPONSE;
    }

    @Override
    public String asText() { return String.valueOf(response); }

    @Override
    public String toString() {
        return "TaskResponseMessage{" +
                "task_id='" + task_id + '\'' +
                ", response=" + response +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskResponseMessage that = (TaskResponseMessage) o;

        if (response != that.response) return false;
        if (task_id != null ? !task_id.equals(that.task_id) : that.task_id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = task_id != null ? task_id.hashCode() : 0;
        result = 31 * result + response;
        return result;
    }
}