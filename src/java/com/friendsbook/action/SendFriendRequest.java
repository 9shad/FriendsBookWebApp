package com.friendsbook.action;

import java.util.Scanner;

import com.friendsbook.DAO.FriendDAO;
import com.friendsbook.DAO.RegisterUserDAO;
import com.friendsbook.pojo.UserFriendRequest;

public class SendFriendRequest {

	public void sendRequest(String userId){
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter User Id to send Request: ");
		String toUserId = sc.next();
		//TODO: ensure logged in userID and toUserId is different
		if(RegisterUserDAO.checkUserId(toUserId)){
			UserFriendRequest obj = new UserFriendRequest();
			obj.setFromUserId(userId);
			obj.setToUserId(toUserId);		
			if(FriendDAO.sendFriendRequestDAO(obj)){
				System.out.println("\n---- Friend Request sent successfully! ----");
			}else{
				System.out.println("\nOops! something went wrong, Please try again!!");
			}
		}else{
			System.out.println("\nOops!, Invalid User Id. Please try again.");
		}
	}
}
