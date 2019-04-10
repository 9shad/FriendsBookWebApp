package com.friendsbook.action;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.friendsbook.DAO.NotificationDAO;
import com.friendsbook.DAO.UserMessageDAO;
import com.friendsbook.pojo.FriendNotification;
import com.friendsbook.pojo.MessageNotification;
import com.friendsbook.pojo.UserFriendRequest;
import com.friendsbook.pojo.UserInformation;
import com.friendsbook.pojo.UserMessage;
import com.friendsbook.pojo.UserNotification;

public class Notification {
	
	
	public void displayNotifications(UserInformation userInfo, String UserId) {
		
		if(userInfo.getNotificationsForUser() == null || userInfo.getNotificationsForUser().isEmpty()) {
			System.out.println("\n---- No new notification ----");
			return;
		}
		
		int input;
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println();
			this.displayNotificationMenu(userInfo.getNotificationsForUser());
			input = sc.nextInt();
			
			if(input>0 && input<userInfo.getNotificationsForUser().size()+1) {
				UserNotification notification = userInfo.getNotificationsForUser().get(input-1);
				
				if(notification instanceof FriendNotification) {
					UserFriendRequest friendRequest = ((FriendNotification) notification).getFriendRequests();
					if(processFriendRequest(notification.getId(), friendRequest.getFromUserId())) {
						userInfo.getNotificationsForUser().remove(input-1);
						System.out.println("\n---- Friend request accepted successfully!! ----");
					}else {
						System.out.println("\nUnable to process this request at this moment, please try again later");
					}
				}else if(notification instanceof MessageNotification) {
					UserMessage message = ((MessageNotification) notification).getUserMessage();
					if(processMessageRequest(notification.getId(),message)) {
						userInfo.getNotificationsForUser().remove(input-1);
						System.out.println("\n---- Message sent successfully!! ----");
					}else {
						System.out.println("\nUnable to process this request at this moment, please try again later");
					}
				}
			}
			
		}while(input != userInfo.getNotificationsForUser().size()+1);
	}
	
	private void displayNotificationMenu(List<UserNotification> userNotification) {
		System.out.println("----- Notifications -----");
		AtomicInteger index = new AtomicInteger(1);
		for(UserNotification notification : userNotification) {
			if(notification instanceof FriendNotification) {
				UserFriendRequest friendRequest = ((FriendNotification) notification).getFriendRequests();
				System.out.println(index.getAndIncrement() + ". "+ friendRequest.getFromUserId()+" sent you friend request.");
			}else if(notification instanceof MessageNotification) {
				UserMessage message = ((MessageNotification) notification).getUserMessage();
				System.out.println(index.getAndIncrement() + ". "+ message.getFromUser()+" sent you a message.");
			}
		}
		
		System.out.println(index.get() + ". "+ "Go Back");
		System.out.print("Enter your choice to process notification: ");
	}
	
	public boolean processFriendRequest(int notificationId, String fromUserId) {
		System.out.println();
		System.out.print("Accept friend Request from "+fromUserId +"? (y:n) : ");
		Scanner sc = new Scanner(System.in);
		String option = sc.next();
		String status;
		if(option.equalsIgnoreCase("y")) {
			status = UserFriendRequest.ACCEPTED;
		}else {
			status = UserFriendRequest.REJECTED;
		}
		if(NotificationDAO.processFriendRequest(notificationId, status)) {
			return NotificationDAO.processNotification(notificationId);
		}
		
		return false;
	}
	
	public boolean processMessageRequest(int notificationId, UserMessage message) {
		
		System.out.println();
		//UserMessage message = UserMessageDAO.getMessages(notificationId);
		System.out.println("["+ message.getFromUser() +" says]: "+message.getMsgDescription());
		System.out.print("Enter your reply: ");
		Scanner sc = new Scanner(System.in);
		String reply = sc.nextLine();
		
		UserMessage replyMessage = new UserMessage();
		replyMessage.setMsgDescription(reply);
		replyMessage.setFromUser(message.getToUser());
		replyMessage.setToUser(message.getFromUser());

		if(UserMessageDAO.sendMessage(replyMessage)) {
			return NotificationDAO.processNotification(notificationId);
		}
		
		return false;
		
	}
	
}
