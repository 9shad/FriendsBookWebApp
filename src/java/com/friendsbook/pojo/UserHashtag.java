package com.friendsbook.pojo;

public class UserHashtag {
	
	private String hashtag;
	private int post_id;
	
	public String getHashtag() {
		return hashtag;
	}
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	
	public UserHashtag(String hashtag, int post_id) {
		this.hashtag = hashtag;
		this.post_id = post_id;
	}
	public UserHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	public UserHashtag() {
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hashtag == null) ? 0 : hashtag.hashCode());
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
		UserHashtag other = (UserHashtag) obj;
		if (hashtag == null) {
			if (other.hashtag != null)
				return false;
		} else if (!hashtag.equals(other.hashtag))
			return false;
		return true;
	}
	
	
	
}
