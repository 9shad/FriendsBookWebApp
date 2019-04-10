package com.friendsbook.pojo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class UserPost {
	public static final String POST = "post";
	public static final String UPDATE = "update";
	//private static int counter = 0;
	
	private int postId;
	private String type;
	private String userId;
	private String description;
	private LocalDateTime timeStamp;
	private List<UserComment> userComments;
	
	public UserPost() {
		super();
		userComments = new ArrayList<>();
	}
	
	public int getPostId() {
		return postId;
	}
	
	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		if(type.equals(POST) || type.equals(UPDATE))
			this.type = type;
		else
			throw new IllegalArgumentException();
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public List<UserComment> getUserComments() {
		return userComments;
	}
	
	public void setUserComments(List<UserComment> userComments) {
		this.userComments = userComments;
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder("["+this.getUserId()+" posted]: "+this.getDescription());
		for(UserComment c : this.getUserComments())
			output.append("\n"+c);
		return output.toString();
	}

	
}
