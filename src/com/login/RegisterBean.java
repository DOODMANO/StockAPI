package com.login;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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

    public String validateUser() {
        boolean valid = RegisterDAO.validate(username, password, role, firstname, lastname, address, phonenumber, email);
        if (valid == true) {
        	if(this.role.equals("user"))
            {
        		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Registration Successful.",""));
        		return "index";
            }
        	if(this.role.equals("manager")) //send request to admin
            {
        		return "managerhome?faces-redirect=true";
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


}
