package com.friendsbook.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.friendsbook.pojo.User;
import com.friendsbook.pojo.UserFriend;
import com.friendsbook.pojo.UserPost;
import com.friendsbook.datasource.Connector;

public class UpdateProfileDAO {

	public static boolean updateUserProfileDAO(UserFriend user, String changeLog){
		Connection con = null;
		PreparedStatement ps = null;
		final String QUERY = "update useraccount set name = ?, gender = ?, school_name = ?, birthday = ?, email = ? where user_id=?";
		
		try {
			con = Connector.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(QUERY);
			ps.setString(1, user.getName());
			ps.setString(2, user.getGender());
			ps.setString(3, user.getSchool());
			ps.setDate(4, java.sql.Date.valueOf(user.getBirthdayDate()));
                        ps.setString(5, user.getEmail());
			ps.setString(6, user.getUserId());
			if(ps.executeUpdate()==1){
				//create an entry in user_post table once the profile information is updated
				UserPost post = new UserPost(); 
				post.setType(UserPost.UPDATE);
				post.setUserId(user.getUserId());
				post.setTimeStamp(LocalDateTime.now());
				post.setDescription(changeLog);

				if(UserPostDAO.createPostDAO(post)) { //check if commit in createpost is needed
					con.commit();
					return true;
				}else {
					con.rollback();
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
}
