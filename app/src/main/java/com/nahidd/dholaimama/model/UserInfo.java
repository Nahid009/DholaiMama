package com.nahidd.dholaimama.model;

public class UserInfo {

    private String user_id, userName,userEmail,userContract,location,date_of_sub;


    public UserInfo() {
    }

    public UserInfo(String user_id, String userName, String userEmail, String userContract, String location, String date_of_sub) {
        this.user_id = user_id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userContract = userContract;
        this.location = location;
        this.date_of_sub = date_of_sub;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getUserContract() {
        return userContract;
    }

    public void setUserContract(String userContract) {
        this.userContract = userContract;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate_of_sub() {
        return date_of_sub;
    }

    public void setDate_of_sub(String date_of_sub) {
        this.date_of_sub = date_of_sub;
    }
}
