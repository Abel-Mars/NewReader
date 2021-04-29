package com.example.newreader.domain;

import lombok.Data;

@Data
public class User {

    private String username="未登录";
    private String password="";

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
