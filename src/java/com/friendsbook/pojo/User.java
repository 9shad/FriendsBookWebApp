package com.friendsbook.pojo;

import java.time.LocalDate;

import com.friendsbook.util.DateAndTime;
import java.util.Objects;

public class User extends UserFriend{
	private String password;
	private String accountCreatedTimeStamp;
	
	public User() {
		super();
		accountCreatedTimeStamp = DateAndTime.dateTimeString();
	}
	
	public User(String userId, String password, String name, String gender,
			String schoolName, LocalDate birthdayDate, String email) {
		super(userId,name,gender,schoolName,birthdayDate,email);
		this.password = password;
		this.accountCreatedTimeStamp = DateAndTime.dateTimeString();
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountCreatedTimeStamp() {
		return accountCreatedTimeStamp;
	}
	public void setAccountCreatedTimeStamp(String accountCreatedTimeStamp) {
		this.accountCreatedTimeStamp = accountCreatedTimeStamp;
	}	

	@Override
	public String toString() {
		return getUserId()+ " profile information:\nname=" + getName() + ",\n gender=" + getGender() + ",\n schoolName="
				+ getSchool() + ",\n birthdayDate=" + getBirthdayDate();
	}
}
