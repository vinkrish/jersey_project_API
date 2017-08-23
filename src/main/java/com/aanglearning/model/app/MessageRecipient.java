package com.aanglearning.model.app;

public class MessageRecipient {
	private long id;
	private long recipientId;
	private String recipientName;
	private String role;
	private long groupId;
	private long messageId;
	private boolean read;
	private long readAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(long recipientId) {
		this.recipientId = recipientId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public long getReadAt() {
		return readAt;
	}

	public void setReadAt(long readAt) {
		this.readAt = readAt;
	}

}
