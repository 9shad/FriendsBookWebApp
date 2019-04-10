package com.friendsbook.pojo;

import java.time.LocalDateTime;

public class UserMessage {
	private int id;
	private String fromUser;
	private String toUser;
	private String msgDescription;
	private LocalDateTime timeStamp;
	private int notificationId;
	
	public UserMessage() {
		this.timeStamp = LocalDateTime.now();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getMsgDescription() {
		return msgDescription;
	}

	public void setMsgDescription(String msgDescription) {
		this.msgDescription = msgDescription;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String toString() {
		return "[" + msgDescription + "]";
	}
	
	public String displaySingleHistoryMessage() {
			return "[" + this.fromUser + ": "+ msgDescription + "]";
	}

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}
	
}
