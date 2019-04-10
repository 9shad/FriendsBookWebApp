package com.friendsbook.action;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import com.friendsbook.DAO.FriendDAO;
import com.friendsbook.pojo.UserFriend;

public class ShowFriendList {
	private List<String> friendList;
	
	public ShowFriendList(String userId) {
		friendList = FriendDAO.getFriendList(userId);
	}
	
	public ShowFriendList(List<String> friendList) {
		this.friendList = friendList;
	}
	
	public void displayFriendList(){
		if(this.friendList != null && !this.friendList.isEmpty()) {
			AtomicInteger counter = new AtomicInteger(0);
			System.out.println("\n------Friend List-----");
			friendList.forEach(obj -> {System.out.println(counter.incrementAndGet() +". "+obj);});
			System.out.println(counter.incrementAndGet()+". Go Back");
		}
	}
	
	public void displayFriendsAndProfile(){
		Scanner sc = new Scanner(System.in);
		int option = 0;
		if(this.friendList!=null && !this.friendList.isEmpty()) {
			do {
				this.displayFriendList();
				System.out.print("Enter your choice to: ");
				option = sc.nextInt();
				if(option >=1 && option < this.friendList.size()+1) {
					//UserFriend friendProfile = new UserFriend(friendList.get(option-1));
					UserFriend friendProfile = FriendDAO.getFriendProfile(friendList.get(option-1));
					System.out.println("------Profile Information-----");
					System.out.println(friendProfile);
				}
			}while(option <= this.friendList.size());
		}else {
			System.out.println("\n---- Looks like you are new to Friends book, Try to send friend request first! ----");
		}
	}

	public List<String> getFriendList() {
		return friendList;
	}
	
	
}
