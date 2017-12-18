package com.login;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class UserBean {

    private String username;
    private String password;
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
        boolean valid = LoginDAO.validate(username, password, role);
        if (valid == true) {
        	if(this.role.equals("user"))
            {
        		return "userhome?faces-redirect=true";
            }
        	if(this.role.equals("manager"))
            {
        		return "userhome?faces-redirect=true";
            }
        	if(this.role.equals("admin"))
            {
        		return "adminhome?faces-redirect=true";
            }
        	else
        	{
        		System.out.println(this.role);
        		System.out.println("ultimate failure");
        		return "index";
        	}
        } else {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect Username, Password or Role. Please enter correct them.",""));
            return "index";
        }
    }

    // logout event, invalidate session
    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }


}
