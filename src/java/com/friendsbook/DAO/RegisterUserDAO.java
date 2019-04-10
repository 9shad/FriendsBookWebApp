package com.friendsbook.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.friendsbook.pojo.User;
import com.friendsbook.datasource.Connector;

public class RegisterUserDAO {
	
	public static boolean addUserToDB(User user){
		Connection con = null;
		PreparedStatement ps = null;
		final String QUERY = "insert into useraccount values (?,?,?,?,?,?,?,?)";
		try {
			con = Connector.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(QUERY);
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getGender());
			ps.setString(5, user.getSchool());
			ps.setDate(6, java.sql.Date.valueOf(user.getBirthdayDate()));
			ps.setString(7, user.getEmail());
			ps.setString(8, user.getAccountCreatedTimeStamp());
			int result = ps.executeUpdate();
			if(result == 1){
				con.commit();
				return true;
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
				ps.close();
				//con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static boolean checkUserId(String userId){
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final String QUERY = "select count(user_id) from useraccount where user_id = ?";
		
		try {
			con = Connector.getConnection();
			ps = con.prepareStatement(QUERY);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			if(rs.next() && rs.getInt(1) >0){
				return true;
			}
		} catch (SQLException e) {
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
		return false;
	}
}
