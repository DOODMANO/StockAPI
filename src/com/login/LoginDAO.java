package com.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.context.FacesContext;

public class LoginDAO {

    public static boolean validate(String username, String password, String role) {
        DataConnect db = null;
        Connection con = null;
        PreparedStatement ps = null;

        try {
            db = DataConnect.getInstance();
            con = db.getCon();
            ps = con.prepareStatement("select * from users where username = ? and password = ? and role = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", rs.getString("username"));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uid", rs.getString("uid"));
                //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("role", rs.getString("role"));
                //System.out.println("uid: " + rs.getString("uid"));
                //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key,object);
                //DataConnect.close(con);
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return false;
        } finally {

        }
        return false;
    }

}
