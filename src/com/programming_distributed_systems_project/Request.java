package com.programming_distributed_systems_project;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private String request;
    private String operation;
    private String username;
    private String password;

    public Request(String request, String operation) {
        super();
        this.request = request;
        this.operation = operation;
    }

    public Request(String username, String password, String operation) {
        super();
        this.operation = operation;
        this.username = username;
        this.password = password;
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
}
