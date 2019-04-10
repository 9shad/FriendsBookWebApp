package com.friendsbook.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.friendsbook.pojo.UserMessage;
import com.friendsbook.pojo.UserNotification;
import com.friendsbook.datasource.Connector;

public class UserMessageDAO {
	
	public static List<UserMessage> getHistoryMessages(String fromUserId, String toUserId){
		
		List<UserMessage> historyMessages = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final String QUERY = "select id, from_userid, to_userid, description, timestamp from user_message where from_userid IN (?,?) and to_userid IN (?,?)";
		
		try {
			con = Connector.getConnection();
			ps = con.prepareStatement(QUERY);
			ps.setString(1, fromUserId);
			ps.setString(2, toUserId);
			ps.setString(3, fromUserId);
			ps.setString(4, toUserId);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				UserMessage msg = new UserMessage();
				msg.setId(rs.getInt("id"));
				msg.setFromUser(rs.getString("from_userid"));
				msg.setToUser(rs.getString("to_userid"));
				msg.setMsgDescription(rs.getString("description"));
				msg.setTimeStamp(rs.getTimestamp("timestamp").toLocalDateTime());
				historyMessages.add(msg);
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
		
		return historyMessages;
	}
	
	
	public static boolean sendMessage(UserMessage message){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final String QUERY = "insert into user_message (from_userid, to_userid, description, timestamp, notification_id) values (?,?,?,?,?)";
		
		try {
			con = Connector.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(QUERY);
			ps.setString(1, message.getFromUser());
			ps.setString(2, message.getToUser());
			ps.setString(3, message.getMsgDescription());
			ps.setTimestamp(4, java.sql.Timestamp.valueOf(message.getTimeStamp()));
			
			UserNotification userNotification = new UserNotification();
			userNotification.setNotificationType(UserNotification.NEW_MSG);
			userNotification.setUserId(message.getToUser());
			message.setNotificationId(NotificationDAO.createNotificationDAO(userNotification));
			
			if(message.getNotificationId() == -1){
				return false;
			}
			
			ps.setInt(5, message.getNotificationId());
			
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
	
	
	public static UserMessage getMessages(int notificationId){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final String QUERY = "select * from user_message where notification_id = ?";
		UserMessage message= null;;
		try {
			con = Connector.getConnection();
			ps = con.prepareStatement(QUERY);
			ps.setInt(1, notificationId);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				message= new UserMessage();
				message.setId(rs.getInt("id"));
				message.setFromUser(rs.getString("from_userid"));
				message.setToUser(rs.getString("to_userid"));
				message.setMsgDescription(rs.getString("description"));
				message.setTimeStamp(rs.getTimestamp("timestamp").toLocalDateTime());
				message.setNotificationId(rs.getInt("notification_id"));
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
		
		return message;
		
	}
	
}
