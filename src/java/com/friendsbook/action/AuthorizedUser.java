package com.friendsbook.action;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.friendsbook.DAO.UserInformationDAO;
import com.friendsbook.pojo.UserFriend;
import com.friendsbook.pojo.UserInformation;

public class AuthorizedUser {
	
	private void showApplicationFeatures(String userName, int notificationCount){
		System.out.println();
		System.out.println("######################################");
		System.out.println("\tWelcome back "+ userName +"!!");
		System.out.println("######################################");
		System.out.println("1. Select an update and post");
		System.out.println("2. Check Notifications ("+notificationCount+" new)");
		System.out.println("3. Create a new post");
		System.out.println("4. View Friends List");
		System.out.println("5. Update Profile");
		System.out.println("6. Send a Message");
		System.out.println("7. Send a Friend Request");
		System.out.println("8. See Hashtag in trends");
		System.out.println("9. Logout");
		System.out.print("Please Enter your choice: ");
	}
	
	public void provideAccess(UserFriend user){
		Scanner sc = new Scanner(System.in);
		int optionSelected = 0;			
		UserInformation userInfo = UserInformationDAO.initialize(user);
		CreatePost createPost = new CreatePost();
		createPost.showMostRecentPostMenu(userInfo.getPostsForUser());
		do{

			showApplicationFeatures(user.getName(), userInfo.getNotificationsForUser().size());
			try {
				optionSelected = sc.nextInt();
			}catch(InputMismatchException ime) {
				continue;
			}
			
			switch(optionSelected){
			case 1:
				createPost.showMostRecentPost(userInfo.getPostsForUser(),user.getUserId());
				break;
			case 2:
				Notification notification = new Notification();
				notification.displayNotifications(userInfo, user.getUserId());
				break;
			case 3:
				createPost.createPost(user.getUserId());
				break;
			case 4:
				ShowFriendList showList = new ShowFriendList(user.getUserId());
				//ShowFriendList showList = new ShowFriendList(userInfo.getUserFriendList()); to use this we need to add newly accepted friend request to this arraylist
				showList.displayFriendsAndProfile();
				break;
			case 5:
				UpdateProfile updateProfile = new UpdateProfile();
				System.out.println(updateProfile.updateProfile(user));
				userInfo.setUser(user);
				break;
			case 6:
				SendMessage sendMessage = new SendMessage();
				sendMessage.sendMessage(user.getUserId());
				break;
			case 7:
				SendFriendRequest sendRequest = new SendFriendRequest();
				sendRequest.sendRequest(user.getUserId());
				break;
			case 8:
				Hashtag hashTag = new Hashtag();
				hashTag.showHashtags(user.getUserId());
				break;
			case 9:
				user = null;
				userInfo = null;
				break;
			default:
				break;
			}		
		}while(optionSelected !=9);
	}
}
