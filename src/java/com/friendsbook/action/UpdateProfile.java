package com.friendsbook.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

import com.friendsbook.DAO.UpdateProfileDAO;
import com.friendsbook.pojo.User;
import com.friendsbook.pojo.UserFriend;

public class UpdateProfile {

	public String updateProfile(UserFriend usr){
		UserFriend user = new User();
		StringBuilder description = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.print("Do you wish to update name? [y:n] : ");
			if(br.readLine().equalsIgnoreCase("y")){
				System.out.print("Enter name: ");
				user.setName(br.readLine());
				description.append("Updated name to: "+user.getName()+". ");
			}
			System.out.print("Do you wish to update gender? [y:n] : ");
			if(br.readLine().equalsIgnoreCase("y")){
				if(usr.getGender().equals("male")){
					System.out.println("Gender updated to female");
					user.setGender("female");
				}else if(usr.getGender().equals("female")){
					System.out.println("Gender updated to male");
					user.setGender("male");
				}
				description.append("Updated gender to : "+user.getGender()+". ");
			}
			
			System.out.print("Do you wish to update school? [y:n] : ");
			if(br.readLine().equalsIgnoreCase("y")){
				System.out.print("Enter school name: ");
				user.setSchool(br.readLine());
				description.append("Updated school to: " + user.getSchool()+". ");
			}
			
			System.out.print("Do you wish to update birthday? [y:n] : ");
			if(br.readLine().equalsIgnoreCase("y")){
				System.out.print("Enter month: (1 to 12): ");
				int month = Integer.parseInt(br.readLine());
				if(month <1 || month >12){
					return "Invalid Month!";
				}
				System.out.print("Enter day: (1 to 31): ");
				int day = Integer.parseInt(br.readLine());
				if(day <1 && day>31){
					return "Invalid Day";
				}else if(month ==2 && day > 28){
					return "Invalid Day";
				}
				System.out.print("Enter year: (YYYY): ");
				int year = Integer.parseInt(br.readLine());
				if(year > LocalDate.now().getYear()){
					return "Invalid Year";
				}else if(year ==  LocalDate.now().getYear() && month > LocalDate.now().getMonthValue()){
					return "Invalid Year";
				}
				//YYYY-MM-DD
				user.setBirthdayDate(LocalDate.of(year, month, day));
				description.append("Updated birthdate to: " + user.getBirthdayDate()+". ");
			}
			
			if (user.getName() != null)
				usr.setName(user.getName());
			if(user.getGender() != null)
				usr.setGender(user.getGender());
			if(user.getSchool() != null)
				usr.setSchool(user.getSchool());
			if(user.getBirthdayDate() != null)
				usr.setBirthdayDate(user.getBirthdayDate());
			
			if(description.length() != 0){
				if(updateProfile(usr, description.toString())){
					return "\n---- Profile updated successfully! ----";
				}else{
					return "\nOops!! Profile Not updated, please try again";
				}
			}else{
				return "\nOops!! Profile Not updated, please try again";
			}
		}catch(IOException ioe) {
			return "\n"+ioe.getMessage();
		}
		
	}
	
	private boolean updateProfile(UserFriend user, String changeLog){
		return UpdateProfileDAO.updateUserProfileDAO(user, changeLog);
	}
	
}
