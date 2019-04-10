package com.friendsbook.action;

import java.io.Console;
import java.util.Scanner;

import com.friendsbook.DAO.LoginDAO;
import com.friendsbook.pojo.UserFriend;


public class AuthenticateUser {

	public void login(){
		Scanner sc = new Scanner(System.in);
		Console cnsl = System.console();
		System.out.print("Enter User Id: ");
		String userId = sc.next();
		System.out.print("Enter Password: ");
		String password = null;
		if(cnsl != null){
			 password = cnsl.readPassword().toString();
		}else{
			password = sc.next();
		}
		
		UserFriend user = this.login(userId, password);
		if(user != null){
			AuthorizedUser au = new AuthorizedUser();
			au.provideAccess(user);
		}else{
			System.out.println("Invalid Credentials, please try again!");
		}
		
	}
	
	private UserFriend login(String userId, String password){
		return LoginDAO.checkUserCredentials(userId, password);
	}
	
	
	
	
}
