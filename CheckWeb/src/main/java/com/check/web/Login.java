package main.java.com.check.web;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 08.03.14
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */
@SessionScoped
@ManagedBean(name = "login")
public class Login {

    @Resource(lookup = "check_resource")
    DataSource dataSource;

    private String username;
    private String password;
    private boolean loggedIn;

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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String login() {
        ResultSet resultSet = null;
        Connection con = null;
        try {
            con = dataSource.getConnection();
            if (con == null)
                throw new SQLException("Can't get database connection");
            PreparedStatement ps
                    = con.prepareStatement(
                    "select id from users where login=? and password_hash=? and is_admin = ?");
            ps.setString(1, username);
            ps.setString(2, md5(password));
            ps.setInt(3, 1);
            resultSet = ps.executeQuery();
            if (resultSet != null && resultSet.next()) {
                loggedIn = true;
                return "/secured/addcheck?faces-redirect=true";
            } else {
                showMessage(getMessage("loginError"));
                return "login";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showMessage(e.getLocalizedMessage());
            return "login";
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showMessage(String message) {
        FacesMessage msg = new FacesMessage(getMessage(message), "ERROR MSG");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private String getMessage(String value) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages_ru_RU");
        return resourceBundle.getString(value);
    }

    private static String md5(final String s) {
        if (s != null) {
            try {
                // Create MD5 Hash
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(s.getBytes());
                byte messageDigest[] = digest.digest();

                // Create Hex String
                StringBuilder hexString = new StringBuilder();
                for (int i = 0; i < messageDigest.length; i++) {
                    String h = Integer.toHexString(0xFF & messageDigest[i]);
                    while (h.length() < 2)
                        h = "0" + h;
                    hexString.append(h);
                }
                return hexString.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
