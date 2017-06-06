package co.syntax.firebasedemo.model;

import java.io.Serializable;

/**
 * Created by rukarayan on 18-Jan-17.
 */

public class User {
    public String userId;
    public String userName;
    public String userEmail;
    public String userPassword;
    private String image;

    public User() {

    }

    public User(String userId, String userName, String userEmail, String userPassword, String image) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.image = image;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
