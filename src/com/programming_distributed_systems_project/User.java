package com.programming_distributed_systems_project;

import java.io.Serializable;

/**
 * This class represents each user in users stored on the server
 * Basically tells java properties and methods availabe to a user
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private int userId;
    private boolean hasTeam;

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
    public void setHasTeam(boolean hasTeam) {
        this.hasTeam = hasTeam;
    }
    public boolean getHasTeam() { return this.hasTeam; }
}
