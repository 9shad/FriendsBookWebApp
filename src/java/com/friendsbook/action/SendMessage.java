package com.friendsbook.action;

import java.util.List;
import java.util.Scanner;

import com.friendsbook.DAO.UserMessageDAO;
import com.friendsbook.pojo.UserMessage;

public class SendMessage {
	
	public void sendMessage(String fromUserId){
		ShowFriendList friends = new ShowFriendList(fromUserId);
		List<String> friendsList = friends.getFriendList();
		
		if(friendsList != null && !friendsList.isEmpty()) {
			Scanner sc = new Scanner(System.in);
			int option = 0;
			do {
				friends.displayFriendList();
				System.out.print("Enter your choice: ");
				option = sc.nextInt();
				if(option >=1 && option < friendsList.size()+1) {
					String toUserId = friendsList.get(option-1);

					//display history messages
					displayHistoryMessage(fromUserId, toUserId);
					
					//accept new message
					System.out.print("Enter message: ");
					sc.nextLine();//clearing the \n character
					String msg = sc.nextLine();
					
					//send message
					sendMessage(fromUserId, toUserId, msg);
				}
				
			}while(option <= friendsList.size());
			
		}else {
			System.out.println("\n---- Looks like you are new to friends book, please try to send friend request first!! ----");
		}
	}
	
	
	public void displayHistoryMessage(String fromUserId, String toUserId) {
		List<UserMessage> historyMessages = UserMessageDAO.getHistoryMessages(fromUserId, toUserId);
		if(historyMessages != null && !historyMessages.isEmpty()) {
			historyMessages.forEach(msg -> {
						System.out.println(msg.displaySingleHistoryMessage());
					});
		}
	}
	
	public void sendMessage(String fromUserId, String toUserId,String msg) {		
		UserMessage userMessage = new UserMessage();
		userMessage.setFromUser(fromUserId);
		userMessage.setToUser(toUserId);
		userMessage.setMsgDescription(msg);
		
		if(UserMessageDAO.sendMessage(userMessage)) {
			System.out.println("Message sent!");
		}else {
			System.err.println("Oops! something went wrong, please try again.");
		}
	}
	
}
