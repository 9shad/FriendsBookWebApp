package com.friendsbook.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.friendsbook.pojo.UserHashtag;
import com.friendsbook.datasource.Connector;

public class HashTagDAO {
	
	public static boolean createHashTagDAO(List<UserHashtag> hashTagsInPost) {
		
		Connection con = null;
		PreparedStatement ps = null;
		StringBuilder QUERY = new StringBuilder("insert into user_hashtag values ");
		
		try {
			for(int index=0;index<hashTagsInPost.size();index++) {
				if(index+1 < hashTagsInPost.size()) {
					QUERY.append("(?,?),");
				}
				else {
					QUERY.append("(?,?)");
				}
			}
			con = Connector.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(QUERY.toString());
			int index = 1;
			for(UserHashtag tag : hashTagsInPost) {
				ps.setString(index++, tag.getHashtag().toLowerCase());
				ps.setInt(index++, tag.getPost_id());
			}
			
			if(ps.executeUpdate() == hashTagsInPost.size()){
				//con.commit(); this gets committed in postDAO class
				return true;
			}			
		}catch (SQLException e) {
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

	//Have not considered ties. so it will display top 3 results at the moment.
	public static List<String> getMostTrendingHashtagDAO() {
		List<String> topHashTags = new ArrayList<>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		final String QUERY = "select hashtag, count(1) from user_hashtag group by hashtag order by 2 desc limit 5";
		
		try {
			con = Connector.getConnection();
			ps = con.prepareStatement(QUERY);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				topHashTags.add(rs.getString("hashtag"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return topHashTags;
	}
}
