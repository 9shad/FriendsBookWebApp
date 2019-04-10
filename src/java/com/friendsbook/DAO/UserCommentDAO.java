package com.friendsbook.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.friendsbook.pojo.UserComment;
import com.friendsbook.datasource.Connector;

public class UserCommentDAO {

	public static boolean saveCommentDAO(UserComment comment) {
		Connection con = null;
		PreparedStatement ps = null;
		final String QUERY = "insert into user_comment(postId, from_UserId, description) values (?,?,?)";
		
		try {
			con = Connector.getConnection();
			con.setAutoCommit(false);
			ps=con.prepareStatement(QUERY);
			ps.setInt(1, comment.getPostId());
			ps.setString(2, comment.getUserId());
			ps.setString(3, comment.getDescription());
			
			if(ps.executeUpdate() == 1) {
				con.commit();
				return true;
			}else {
				con.rollback();
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public static List<UserComment> getCommentsDAO(int postId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<UserComment> comments = new ArrayList<UserComment>();
		final String QUERY = "select * from user_comment where postId = ?";
		
		try {
			con = Connector.getConnection();
			con.setAutoCommit(false);
			ps=con.prepareStatement(QUERY);
			ps.setInt(1, postId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				UserComment comment = new UserComment();
				comment.setCommentId(rs.getInt("id"));
				comment.setPostId(rs.getInt("postId"));
				comment.setUserId(rs.getString("from_UserId"));
				comment.setDescription(rs.getString("description"));
				
				comments.add(comment);
			}
			
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return comments;
	}
}
