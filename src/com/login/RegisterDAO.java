package com.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.context.FacesContext;

public class RegisterDAO {

    public static boolean validate(String username, String password, String role, String firstname, String lastname, String address, String phonenumber, String email) {
    	DataConnect db = null;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            db = DataConnect.getInstance();
            con = db.getCon();
            
            ps = con.prepareStatement("INSERT INTO users(firstname, lastname, address, phonenumber, email, username, password, role) VALUES(?,?,?,?,?,?,?,?)");
            if(role.equals("manager")) //send manager to admin's database instead to be approved
            {
            ps = con.prepareStatement("INSERT INTO managerregisters(firstname, lastname, address, phonenumber, email, username, password, role) VALUES(?,?,?,?,?,?,?,?)");
            }
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, address);
            ps.setString(4, phonenumber);
            ps.setString(5, email);
            ps.setString(6, username);
            ps.setString(7, password);
            ps.setString(8, role);          
            
            //int rs = ps.executeUpdate();
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Registration Error -->" + ex.getMessage());
            return false;
        } finally {

        }
    }
    

    
    /*public static boolean accept(String username, String password, String role, String firstname, String lastname, String address, String phonenumber, String email, int uid) {
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = DataConnect.getConnection();
            
            ps = con.prepareStatement("INSERT INTO users(firstname, lastname, address, phonenumber, email, username, password, role) VALUES(?,?,?,?,?,?,?,?)");
            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, address);
            ps.setString(4, phonenumber);
            ps.setString(5, email);
            ps.setString(6, username);
            ps.setString(7, password);
            ps.setString(8, role);          
            
            //int rs = ps.executeUpdate();
            ps.executeUpdate();
            
            String sql = "DELETE FROM managerregisters WHERE uid=" + uid;
            ps= con.prepareStatement(sql); 
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Accept Manager Registration Error -->" + ex.getMessage());
            return false;
        } finally {

        }
    }*/
    /*
    public static boolean reject(int uid) {
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = DataConnect.getConnection();
            
            String sql = "DELETE FROM managerregisters WHERE uid=" + uid;
            ps= con.prepareStatement(sql); 
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Accept Manager Registration Error -->" + ex.getMessage());
            return false;
        } finally {

        }
    }*/

}
