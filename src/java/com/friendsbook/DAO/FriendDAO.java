package com.friendsbook.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.friendsbook.pojo.UserFriend;
import com.friendsbook.pojo.UserFriendRequest;
import com.friendsbook.pojo.UserNotification;
import com.friendsbook.datasource.Connector;

public class FriendDAO {
	public static boolean sendFriendRequestDAO(UserFriendRequest userRequest){
		//TODO: before inserting check if same friend request already exists
		Connection con = null;
		PreparedStatement ps = null;
		final String QUERY = "insert into friend_request(notification_id, from_userid,to_userid,status,timestamp) values(?,?,?,?,?)";
		
		try {
			con = Connector.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(QUERY);
			ps.setString(2, userRequest.getFromUserId());
			ps.setString(3, userRequest.getToUserId());
			ps.setString(4, userRequest.getStatus());
			ps.setTimestamp(5, java.sql.Timestamp.valueOf(userRequest.getTimeStamp()));
			
			UserNotification userNotification = new UserNotification();
			userNotification.setNotificationType(UserNotification.FRIEND_REQUEST);
			userNotification.setUserId(userRequest.getToUserId());
			userRequest.setNotificationId(NotificationDAO.createNotificationDAO(userNotification));
			
			if(userRequest.getNotificationId() == -1){
				return false;
			}
			
			ps.setInt(1, userRequest.getNotificationId());
			
			if(ps.executeUpdate() != 1){
				con.rollback();
				return false;
			}else{
				con.commit();
				return true;
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
	public static List<String> getFriendList(String userId){
		List<String> friendList = new ArrayList<String>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final String QUERY = "select from_userid, to_userid from friend_request where status = ? and (from_userid = ? or to_userid = ?) order by timestamp desc";
		
		try {
			con = Connector.getConnection();
			ps = con.prepareStatement(QUERY);
			ps.setString(1, UserFriendRequest.ACCEPTED);
			ps.setString(2, userId);
			ps.setString(3, userId);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
                            if(rs.getString("from_userid").equals(userId)){
                                if(!rs.getString("to_userid").equals("") && !friendList.contains(rs.getString("to_userid")))
                                    friendList.add(rs.getString("to_userid"));
                            }else if(rs.getString("to_userid").equals(userId)){
                                if(!rs.getString("from_userid").equals("") && !friendList.contains(rs.getString("from_userid")))
                                    friendList.add(rs.getString("from_userid"));
                            }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friendList;
	}
	
	public static UserFriend getFriendProfile(String userId){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserFriend profileInfo = null;
		final String QUERY = "select user_id, name, gender, school_name, birthday, email from useraccount where user_id = ?";
		
		try {
			con = Connector.getConnection();
			ps = con.prepareStatement(QUERY);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			if(rs.next()){
				profileInfo = new UserFriend(
						rs.getString("user_id"),
						rs.getString("name"),
						rs.getString("gender"),
						rs.getString("school_name"),
						rs.getDate("birthday").toLocalDate(),
                                                rs.getString("email")
				);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				ps.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return profileInfo;
	}
	
}
