package com.friendsbook.pojo;

import java.util.ArrayList;
import java.util.List;

public class UserInformation{

    private UserFriend user;
    private List<UserPost> postsForUser;
    private List<String> userFriendList;
    private List<UserNotification> notificationsForUser;

    public UserInformation(UserFriend user) {
            super();
            this.user = user;
            postsForUser = new ArrayList<>();
            userFriendList = new ArrayList<>();
            notificationsForUser = new ArrayList<>();
    }

    public UserFriend getUser() {
            return user;
    }
    public void setUser(UserFriend user) {
            this.user = user;
    }

    public List<UserPost> getPostsForUser() {
            return postsForUser;
    }

    public void setPostsForUser(List<UserPost> postsForUser) {
            this.postsForUser = postsForUser;
    }

    public List<String> getUserFriendList() {
            return userFriendList;
    }

    public void setUserFriendList(List<String> userFriendList) {
            this.userFriendList = userFriendList;
    }

    public List<UserNotification> getNotificationsForUser() {
            return notificationsForUser;
    }

    public void setNotificationsForUser(List<UserNotification> notificationsForUser) {
            this.notificationsForUser = notificationsForUser;
    }

}
