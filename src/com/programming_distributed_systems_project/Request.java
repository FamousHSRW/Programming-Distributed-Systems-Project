package com.programming_distributed_systems_project;

import java.io.Serializable;

/**
 * This class represents all messages sent from client to server
 */
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private String request;
    private String operation;
    private String username;
    private String password;
    private int teamId;
    private int userId;

    /**
     * This request is general // FIXME: should probably be removed
     * General Request
     * @param request
     * @param operation
     */
    public Request(String request, String operation) {
        super();
        this.request = request;
        this.operation = operation;
    }

    /**
     * This request is only created when the user logs in or signs up
     * Login request constructor
     * @param username
     * @param password
     * @param operation
     */
    public Request(String username, String password, String operation) {
        super();
        this.operation = operation;
        this.username = username;
        this.password = password;
    }

    /**
     * This request is only created when a user tries to join a team
     * Join Team Request
     * @param teamId
     * @param userId
     * @param operation
     */
    public Request(int teamId, int userId, String operation) {
        super();
        this.teamId = teamId;
        this.operation = operation;
        this.userId = userId;
    }

    public String getOperation() {
        return operation;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getTeamId() {
        return teamId;
    }
    public int getUserId() { return userId; }
}

