package com.friendsbook.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.friendsbook.pojo.FriendNotification;
import com.friendsbook.pojo.MessageNotification;
import com.friendsbook.pojo.UserFriendRequest;
import com.friendsbook.pojo.UserMessage;
import com.friendsbook.pojo.UserNotification;
import com.friendsbook.datasource.Connector;

public class NotificationDAO {
	
	public static int createNotificationDAO(UserNotification userNotification){
		int result = -1;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final String QUERY = "insert into user_notification (notification_type,status,user_id,timestamp) values(?,?,?,?)";
		
		try {
			con = Connector.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(QUERY,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, userNotification.getNotificationType());
			ps.setString(2, userNotification.getStatus());
			ps.setString(3, userNotification.getUserId());
			ps.setTimestamp(4, java.sql.Timestamp.valueOf(userNotification.getTimeStamp()));
			
			if(ps.executeUpdate() != 1){
				con.rollback();
				return result;
			}
			
			rs = ps.getGeneratedKeys();

		    if (rs.next()) {
		        result = rs.getInt(1);
		    }
		    
		    if(result != -1){
		    	con.commit();
		    	return result;
		    }else{
		    	con.rollback();
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
				rs.close();
				ps.close();
				//con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static boolean processFriendRequest(int notificationId, String status) {
		Connection con = null;
		PreparedStatement ps = null;
		final String QUERY = "update friend_request set status = ? where notification_id = ?";
		
		try {
			con = Connector.getConnection();
			con.setAutoCommit(false);
			ps = Connector.getConnection().prepareStatement(QUERY);
			
			ps.setString(1, status);
			ps.setInt(2, notificationId);
			
			if(ps.executeUpdate() == 1) {
				con.commit();
				return true;
			}else {
				con.rollback();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public static boolean processNotification(int notificationId) {
		Connection con = null;
		PreparedStatement ps = null;
		final String QUERY = "update user_notification set status = ? where id = ?";
		
		try {
			con = Connector.getConnection();
			con.setAutoCommit(false);
			ps = Connector.getConnection().prepareStatement(QUERY);
			
			ps.setString(1, UserNotification.READ);
			ps.setInt(2, notificationId);
			
			if(ps.executeUpdate() == 1) {
				con.commit();
				return true;
			}else {
				con.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public static List<UserNotification> getUnProsessedUserNotification(String userId){
		
		List<UserNotification> notifications = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final String QUERY = "select notification_id, from_userid,to_userid,fr.status "
				+ "from user_notification un, friend_request fr "
				+ "where fr.notification_id = un.id and un.user_id = ? and un.status = ? and notification_type=?";
		
		final String QUERY1 = "select notification_id, from_userid,to_userid,description "
				+ "from user_notification un, user_message um "
				+ "where um.notification_id = un.id and un.user_id = ? and un.status = ? and notification_type = ?";
		
		try {
			notifications = new ArrayList<UserNotification>();
			ps = Connector.getConnection().prepareStatement(QUERY);
			ps.setString(1, userId);
			ps.setString(2, UserNotification.NEW);
			ps.setString(3, UserNotification.FRIEND_REQUEST);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				FriendNotification fn = new FriendNotification();
				fn.setId(rs.getInt("notification_id"));
				fn.setNotificationType(UserNotification.FRIEND_REQUEST);
				fn.setStatus(UserNotification.NEW);
				fn.setUserId(userId);
				
				UserFriendRequest request = new UserFriendRequest();
				request.setNotificationId(rs.getInt("notification_id"));
				request.setStatus(rs.getString("fr.status"));
				request.setFromUserId(rs.getString("from_userid"));
				request.setToUserId(rs.getString("to_userid"));
				
				fn.setFriendRequests(request);
				notifications.add(fn);
			}
			
			ps.close();
			rs.close();
			
			ps = Connector.getConnection().prepareStatement(QUERY1);
			ps.setString(1, userId);
			ps.setString(2, UserNotification.NEW);
			ps.setString(3, UserNotification.NEW_MSG);

			rs = ps.executeQuery();
			
			while(rs.next()) {
				MessageNotification mn = new MessageNotification();
				mn.setId(rs.getInt("notification_id"));
				mn.setNotificationType(UserNotification.NEW_MSG);
				mn.setStatus(UserNotification.NEW);
				mn.setUserId(userId);
				
				UserMessage message = new UserMessage();
				message.setNotificationId(rs.getInt("notification_id"));
				message.setMsgDescription(rs.getString("description"));
				message.setFromUser(rs.getString("from_userid"));
				message.setToUser(rs.getString("to_userid"));
				
				mn.setUserMessage(message);
				
				notifications.add(mn);
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
		
		return notifications;
	}
	
}
