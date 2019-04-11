package com.friendsbook.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connector {

	private final static String URL = "jdbc:mysql://mis-sql.uhcl.edu/sindhwanin0507";
	private final static String USERNAME = "sindhwanin0507";
	private final static String PASSWORD = "1596037";
	
	private static Connection connection;
	
	private Connector() throws SQLException{
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME,PASSWORD);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	
	public static Connection getConnection() throws SQLException{
		
		if(connection == null){
                    new Connector();
		}
		return connection;
	}
}
