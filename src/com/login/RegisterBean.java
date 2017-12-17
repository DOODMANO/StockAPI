package com.login;

//Also handles the Manager Registration Requests

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@ManagedBean
@SessionScoped
public class RegisterBean {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String address;
    private String phonenumber;
    private String email;
    private String role;
    private int uid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String acceptManager(String username, String password, String firstname, String lastname, String address, String phonenumber, String email, int uid)
	{
		DataConnect db = null;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            db = DataConnect.getInstance();
            con = db.getCon();
            String role = "manager";
            
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
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Manager has been accepted.",""));
            return "adminhome";

        } catch (SQLException ex) {
            System.out.println("Accept Manager Registration Error -->" + ex.getMessage());
            return "adminhome";
        } finally {

        }
	}
	
	public String rejectManager(int uid)
	{
		System.out.println("TESTING!!!!!!!!!!!!!!!");
		System.out.println("TESTING!!!!!!!!!!!!!!!" + uid);
		System.out.println("TESTING!!!!!!!!!!!!!!!");
		DataConnect db = null;
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            db = DataConnect.getInstance();
            con = db.getCon();
            
            String sql = "DELETE FROM managerregisters WHERE uid=" + uid;
            ps= con.prepareStatement(sql); 
            ps.executeUpdate();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Manager has been rejected.",""));
            return "adminhome";

        } catch (SQLException ex) {
            System.out.println("Accept Manager Registration Error -->" + ex.getMessage());
            return "adminhome";
        } finally {

        }
	}
	
	
	public List<RegisterBean> viewManagerRegister() {
    //reuse RegisterBean since it has all the aspects of the registered manager
    DataConnect db = null;
	Connection con = null;
    PreparedStatement ps = null;
    List<RegisterBean> list = new ArrayList<RegisterBean>();
    
    try {
        db = DataConnect.getInstance();
        con = db.getCon();
        
        ps = con.prepareStatement("select * from managerregisters");
        ResultSet rs = ps.executeQuery();
        while(rs.next())
        {
        	RegisterBean manager = new RegisterBean();
        	
        	manager.setUid(rs.getInt("uid"));
        	manager.setFirstname(rs.getString("firstname"));
        	manager.setLastname(rs.getString("lastname"));
        	manager.setAddress(rs.getString("address"));
        	manager.setPhonenumber(rs.getString("phonenumber"));
        	manager.setEmail(rs.getString("email"));
        	manager.setUsername(rs.getString("username"));
        	manager.setPassword(rs.getString("password"));
        	
        	list.add(manager);
        }
    	return list;

    } catch (SQLException ex) {
        System.out.println("Data Viewing Error: " + ex.getMessage());
        return list;
    } finally {

    }
}
	
    public String validateUser() {
        boolean valid = RegisterDAO.validate(username, password, role, firstname, lastname, address, phonenumber, email);
        if (valid == true) {
        	if(this.role.equals("user"))
            {
        		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration Successful.",""));
        		return "index";
            }
        	if(this.role.equals("manager")) //send request to admin
            {
        		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Manager Registration Request Sent to Admin.",""));
        		return "index";
            }
        	else
        	{
        		System.out.println(this.role);
        		System.out.println("ultimate failure");
        		return "index";
        	}
        } else {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Something went wrong.",""));
            return "register";
        }
    }

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}


}
