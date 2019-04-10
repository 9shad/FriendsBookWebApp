package com.friendsbook.DAO;

import com.friendsbook.pojo.UserFriend;
import com.friendsbook.pojo.UserInformation;

public class UserInformationDAO {

	public static UserInformation initialize(UserFriend user) {
		UserInformation userInfo = new UserInformation(user);
		userInfo.setUserFriendList(FriendDAO.getFriendList(user.getUserId()));
		userInfo.setNotificationsForUser(NotificationDAO.getUnProsessedUserNotification(user.getUserId()));
		userInfo.setPostsForUser(UserPostDAO.getNewPosts(user.getUserId()));
		return userInfo;
	}
	
}
