package com.programming_distributed_systems_project;

public class Main {
    /**
     * Entry point of client side of application
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Welcome To Team Maker System");
        Client client = new Client();
        UserInterface.loggedOutInterface(client);
    }
}
