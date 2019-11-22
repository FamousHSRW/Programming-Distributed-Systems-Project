package com.programming_distributed_systems_project;

import java.io.Serializable;

public class Reply implements Serializable {
    private static final long serialVersionUID = 1L;

    private String operation;
    private String response;
    public Reply(String response) {
        super();
        this.response = response;
    }
    public String getResponse() {
        return this.response;
    }
}
