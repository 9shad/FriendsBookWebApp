package com.friendsbook.pojo;

import java.time.LocalDate;

import com.friendsbook.util.DateAndTime;

public class User extends UserFriend{
	private String password;
	private String email;
	private String accountCreatedTimeStamp;
	
	public User() {
		super();
		accountCreatedTimeStamp = DateAndTime.dateTimeString();
	}
	
	public User(String userId, String password, String name, String gender,
			String schoolName, LocalDate birthdayDate, String email) {
		super(userId,name,gender,schoolName,birthdayDate);
		this.password = password;
		this.email = email;
		this.accountCreatedTimeStamp = DateAndTime.dateTimeString();
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccountCreatedTimeStamp() {
		return accountCreatedTimeStamp;
	}
	public void setAccountCreatedTimeStamp(String accountCreatedTimeStamp) {
		this.accountCreatedTimeStamp = accountCreatedTimeStamp;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((accountCreatedTimeStamp == null) ? 0
						: accountCreatedTimeStamp.hashCode());
		result = prime * result
				+ ((super.getBirthdayDate() == null) ? 0 : super.getBirthdayDate().hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((super.getGender() == null) ? 0 : super.getGender().hashCode());
		result = prime * result + ((super.getName() == null) ? 0 : getName().hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((super.getSchool() == null) ? 0 : super.getSchool().hashCode());
		result = prime * result + ((super.getUserId() == null) ? 0 : super.getUserId().hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (accountCreatedTimeStamp == null) {
			if (other.accountCreatedTimeStamp != null)
				return false;
		} else if (!accountCreatedTimeStamp
				.equals(other.accountCreatedTimeStamp))
			return false;
		if (getBirthdayDate() == null) {
			if (other.getBirthdayDate() != null)
				return false;
		} else if (!getBirthdayDate().equals(other.getBirthdayDate()))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (getGender() == null) {
			if (other.getGender() != null)
				return false;
		} else if (!getGender().equals(other.getGender()))
			return false;
		if (getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!getName().equals(other.getName()))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (getSchool() == null) {
			if (other.getSchool() != null)
				return false;
		} else if (!getSchool().equals(other.getSchool()))
			return false;
		if (getUserId() == null) {
			if (other.getUserId() != null)
				return false;
		} else if (!getUserId().equals(other.getUserId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getUserId()+ " profile information:\nname=" + getName() + ",\n gender=" + getGender() + ",\n schoolName="
				+ getSchool() + ",\n birthdayDate=" + getBirthdayDate();
	}
	
}
