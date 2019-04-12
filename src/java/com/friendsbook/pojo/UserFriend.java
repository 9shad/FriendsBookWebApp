package com.friendsbook.pojo;

import java.time.LocalDate;

public class UserFriend {
	private String userId;
	private String name;
	private String gender;
	private String school;
	private LocalDate birthdayDate;
        private String email;
	
	public UserFriend() {
		
	}
	
	public UserFriend(String userId, String name, String gender, String school, LocalDate birthdayDate,String email) {
		this.userId = userId;
		this.name = name;
		this.gender = gender;
		this.school = school;
		this.birthdayDate = birthdayDate;
                this.email = email;
	}
        
        public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public void setBirthdayDate(LocalDate birthdayDate) {
		this.birthdayDate = birthdayDate;
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public String getSchool() {
		return school;
	}

	public LocalDate getBirthdayDate() {
		return birthdayDate;
	}

	@Override
	public String toString() {
		return "Friend [name= " + name + ", gender= " + gender + ", school= " + school + ", birthdayDate= "
				+ birthdayDate + "]";
	}

	
}
