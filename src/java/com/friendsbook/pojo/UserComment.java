package com.friendsbook.pojo;

public class UserComment {
	private int commentId;
	private int postId;
	private String userId;
	private String description;
	
	public UserComment(){}
	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
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
		if(description.length() <= 20)
			this.description = description;
		else
			this.description = description.substring(0, 20);
			//throw new SizeLimitExceededException("Comment cannot have more than 20 characters.");
	}
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	@Override
	public String toString() {
		//return "UserComment [description=" + description + "]";
		return "\t" +"->["+ userId +" commented]: "+ description;
	}
	
	
}
