package com.example.nvl.Class;

import java.io.Serializable;

public class User implements Serializable {
    int id;
    String phonenumber;
    String password;
    String username;
    String avatar;

    public User(int id, String phonenumber, String password, String username, String avatar) {
        this.id = id;
        this.phonenumber = phonenumber;
        this.password = password;
        this.username = username;
        this.avatar = avatar;
    }

    public User(String phonenumber, String password, String username, String avatar) {
        this.phonenumber = phonenumber;
        this.password = password;
        this.username = username;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
