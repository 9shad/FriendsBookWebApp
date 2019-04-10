package com.friendsbook.pojo;

import java.time.LocalDateTime;

public class UserNotification {

	public static final String FRIEND_REQUEST = "friend_request";
	public static final String NEW_MSG = "new_msg";
	public static final String READ = "read";
	public static final String NEW = "new";
	
	private int id;
	private String notificationType;
	private String status;
	private String userId;
	private LocalDateTime timeStamp;
	
	public UserNotification() {
		super();
		this.status = NEW;
		this.timeStamp = LocalDateTime.now();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	
}
