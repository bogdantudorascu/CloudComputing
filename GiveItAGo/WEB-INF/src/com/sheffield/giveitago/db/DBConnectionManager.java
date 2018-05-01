package com.sheffield.giveitago.db;

import java.sql.*;

public class DBConnectionManager {

	private Connection con;
	
	public DBConnectionManager(String url, String user, String password){
		//create db connection now
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		return this.con;
	}
	
	public void closeConnection(){
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
