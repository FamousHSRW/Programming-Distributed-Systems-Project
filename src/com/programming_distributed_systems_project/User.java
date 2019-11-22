package com.programming_distributed_systems_project;

public class User {
    private String username;
    private String password;
    private int userId;

    public User (String username, String password, int userId) {
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
