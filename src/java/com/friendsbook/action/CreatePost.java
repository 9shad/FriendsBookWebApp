package com.friendsbook.action;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


import com.friendsbook.DAO.UserCommentDAO;
import com.friendsbook.DAO.UserPostDAO;
import com.friendsbook.pojo.UserComment;
import com.friendsbook.pojo.UserPost;

public class CreatePost {
	
	public void createPost(String userId){
		UserPost post = new UserPost();
		post.setType(UserPost.POST);
		post.setUserId(userId);
		post.setTimeStamp(LocalDateTime.now());
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter your post information: ");
		sc.hasNextLine();//clearing /n character
		post.setDescription(sc.nextLine());
		
		if(createPost(post)){
			System.out.println("\n---- Post created successfully! ----");
		}else{
			System.out.println("\nOops! something went wrong, please try again.");
		}
	}

	private boolean createPost(UserPost post){
		return UserPostDAO.createPostDAO(post);
	}
	
	
	public void showMostRecentPostMenu(List<UserPost> posts) {
		if(posts!=null && !posts.isEmpty()) {
			AtomicInteger index = new AtomicInteger(1);
			System.out.println();
			System.out.println("---- Most Recent Post from your friends ----");
			posts.forEach(p -> { 
				System.out.println(index.getAndIncrement() +". "+p);
				//System.out.println(index.getAndIncrement() +". ["+p.getUserId()+"]: "+p.getDescription());
				//p.getUserComments().forEach(c->{System.out.println(c);});
			});
		}
	}
	
	public void showMostRecentPost(List<UserPost> posts, String userId) {
		Scanner sc = new Scanner(System.in);
		if(posts!=null && !posts.isEmpty()) {
			int input;
			do {
				showMostRecentPostMenu(posts);
				System.out.println(posts.size()+1 + ". Go Back");
				System.out.print("Enter your choice to leave comment:");
				input = sc.nextInt();
				if(input >0 && input <= posts.size()) {
					System.out.print("Enter your comment for this post");
					sc.nextLine();
					String comment =sc.nextLine();
					UserComment userComment = new UserComment();
					userComment.setUserId(userId);
					userComment.setDescription(comment);
					userComment.setPostId(posts.get(input-1).getPostId());
					
					if(UserCommentDAO.saveCommentDAO(userComment)) {
						posts.get(input-1).getUserComments().add(userComment);
					}
				}
				
			}while(input > 0 && input < posts.size()+1);
			
		}else {
			System.out.println("\n----No posts to display at the moment----");
		}
	}
	
}
