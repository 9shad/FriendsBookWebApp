package com.friendsbook.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.friendsbook.pojo.UserComment;
import com.friendsbook.pojo.UserFriendRequest;
import com.friendsbook.pojo.UserHashtag;
import com.friendsbook.pojo.UserPost;
import com.friendsbook.datasource.Connector;

public class UserPostDAO {
	
	public static boolean createPostDAO(UserPost post){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final String QUERY = "insert into user_post(post_type,user_id,description,timestamp) values (?,?,?,?)";
		int postId = -1;
		List<UserHashtag> tags = new ArrayList<>();
		try {
			con = Connector.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(QUERY,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, post.getType());
			ps.setString(2, post.getUserId());
			ps.setString(3, post.getDescription());
			ps.setTimestamp(4, Timestamp.valueOf(post.getTimeStamp()));
			
			if(ps.executeUpdate() == 1){
				
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
			        postId = rs.getInt(1);
			    }
				//postId != -1 see if it needs to be checked
				if(post.getDescription().contains("#")){
					String[] temp = post.getDescription().split(" "); //we can also use subsrting to get index of # and next space for getting hashtag
					for(int i=0;i<temp.length;i++) {
			        	if(temp[i].startsWith("#")) {
			        		UserHashtag hashTag = new UserHashtag(temp[i],postId);
			        		tags.add(hashTag);
			        	}
			        }
					if(tags.size() == 0 || HashTagDAO.createHashTagDAO(tags)) {
			        	con.commit();
				    	return true;
			        }else {
			        	con.rollback();
			        }
				}else {
					con.commit();
			    	return true;
				}
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				//con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}	
	
	
	
	public static List<UserPost> getNewPosts(String userId){
		List<UserPost> posts = null;
		//can also use dense_rank() instead of writing two queries. but this function is supported for mysql verson above 8
		final String QUERY = "select * from user_post "
				+ "where post_type = ? AND (user_id IN (select from_userid from friend_request where to_userid = ? and status=? )"
				+ " OR user_id IN (select to_userid from friend_request where from_userid = ? and status=? )) order by timestamp desc limit 3";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try {
			posts = new ArrayList<UserPost>();
			ps = Connector.getConnection().prepareStatement(QUERY);
			ps.setString(1, UserPost.POST);
			ps.setString(2, userId);
			ps.setString(3, UserFriendRequest.ACCEPTED);
			ps.setString(4, userId);
			ps.setString(5, UserFriendRequest.ACCEPTED);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				UserPost post = new UserPost();
				
				post.setPostId(rs.getInt("id"));
				post.setType(UserPost.POST);
				post.setUserId(rs.getString("user_id"));
				post.setDescription(rs.getString("description"));
                                post.setTimeStamp(rs.getTimestamp("timestamp").toLocalDateTime());
				
				List<UserComment> comments = UserCommentDAO.getCommentsDAO(rs.getInt("id"));
				if(comments != null && !comments.isEmpty()) {
					post.setUserComments(comments);
				}
				
				posts.add(post);				
			}
			
			ps.close();
			rs.close();
			
			ps = Connector.getConnection().prepareStatement(QUERY);
			ps.setString(1, UserPost.UPDATE);
			ps.setString(2, userId);
			ps.setString(3, UserFriendRequest.ACCEPTED);
			ps.setString(4, userId);
			ps.setString(5, UserFriendRequest.ACCEPTED);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				UserPost post = new UserPost();
				
				post.setPostId(rs.getInt("id"));
				post.setType(UserPost.UPDATE);
				post.setUserId(rs.getString("user_id"));
				post.setDescription(rs.getString("description"));
                                post.setTimeStamp(rs.getTimestamp("timestamp").toLocalDateTime());
				
				List<UserComment> comments = UserCommentDAO.getCommentsDAO(rs.getInt("id"));
				if(comments != null && !comments.isEmpty()) {
					post.setUserComments(comments);
				}
				
				posts.add(post);				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return posts;
	}
	
	
	public static List<UserPost> getPostsContainingHashtag(String userId, String hashtag){
		List<UserPost> posts = null;
		final String QUERY = "select * from user_post "
				+ "where post_type = ? AND (user_id IN (select from_userid from friend_request where to_userid = ? and status=? )"
				+ " OR user_id IN (select to_userid from friend_request where from_userid = ? and status=? )) AND description LIKE ?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			posts = new ArrayList<UserPost>();
			ps = Connector.getConnection().prepareStatement(QUERY);
			ps.setString(1, UserPost.POST);
			ps.setString(2, userId);
			ps.setString(3, UserFriendRequest.ACCEPTED);
			ps.setString(4, userId);
			ps.setString(5, UserFriendRequest.ACCEPTED);
			ps.setString(6, "%"+hashtag+"%");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				UserPost post = new UserPost();
				
				post.setPostId(rs.getInt("id"));
				post.setType(UserPost.POST);
				post.setUserId(rs.getString("user_id"));
				post.setDescription(rs.getString("description"));
				
				List<UserComment> comments = UserCommentDAO.getCommentsDAO(rs.getInt("id"));
				if(comments != null && !comments.isEmpty()) {
					post.setUserComments(comments);
				}
				
				posts.add(post);				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return posts;
	}
}
