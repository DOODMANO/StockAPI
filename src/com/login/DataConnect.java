package com.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DataConnect {
	private static DataConnect instance;
	private Connection con = null;
	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/csi518db,root,root");
            return con;
        } catch (Exception ex) {
            System.out.println("Database.getConnection() Error -->" + ex.getMessage());
            return null;
        }
    }
	
	
	private DataConnect()
	{
	
	try {
		com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
	
		ds.setServerName(System.getenv("ICSI518_SERVER"));
		ds.setPortNumber(Integer.parseInt(System.getenv("ICSI518_PORT")));
		ds.setDatabaseName(System.getenv("ICSI518_DB"));
		ds.setUser(System.getenv("ICSI518_USER"));
		ds.setPassword(System.getenv("ICSI518_PASSWORD"));
		
		/*
		ds.setServerName("localhost");
		ds.setPortNumber(3306);
		ds.setDatabaseName("csi518db");
		ds.setUser("CSI518_USER");
		ds.setPassword("CSI518_PASSWORD");
		*/
		// Get a connection object
		this.con = ds.getConnection();
		}
	catch (Exception e) {
		System.err.println("Exception: " + e.getMessage());
	} 
	finally { }
	}
	
	public static DataConnect getInstance()
	{
		if(instance == null)
		{
			instance = new DataConnect();
		}
		return instance;
		}
	
    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
	
	public void finalize()
	{
	try 
	{	
		con.close();
	}
	catch(SQLException e)
	{
		
	}
	}
}