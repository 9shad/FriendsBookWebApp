package com.friendsbook.action;

import java.util.List;
import java.util.Scanner;

import com.friendsbook.DAO.HashTagDAO;
import com.friendsbook.DAO.UserPostDAO;
import com.friendsbook.pojo.UserPost;

public class Hashtag {
	
	private List<String> getMostTrendingHashTags(){
		return HashTagDAO.getMostTrendingHashtagDAO();
	}
	
	public void showHashtags() {
		System.out.println("\n----Top three trending hashtag----");
		getMostTrendingHashTags().forEach(obj -> {System.out.println("-> "+obj);});
	}
	
	
	public void showHashtags(String userId) {
		Scanner sc = new Scanner(System.in);
		
		this.showHashtags();
		System.out.print("Enter a hashtag to display related posts: ");
		String input = sc.next();
		if(input!=null && input.charAt(0)!='#'){
			input = "#" + input;
		}
		
		List<UserPost> posts = UserPostDAO.getPostsContainingHashtag(userId, input);
		
		if(posts != null && posts.size() >0) {
			System.out.println("\n----- Posts with hashtag "+input+" from friends -----");
			posts.forEach(p -> { 
				System.out.println(p);
				//System.out.println("-> ["+p.getUserId()+"]: "+p.getDescription());
				//p.getUserComments().forEach(c->{System.out.println(c);});
			});
		}else {
			System.out.println("\nOops! could not find any post from your friends with hashtag "+input);
		}

	}
	
}
