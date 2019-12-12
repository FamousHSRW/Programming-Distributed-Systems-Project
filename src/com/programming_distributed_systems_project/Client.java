package com.programming_distributed_systems_project;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private  static Scanner scanner = new Scanner(System.in);
    private User user = null;
    private Socket connection;

    public Client(Socket connection) {
        this.connection = connection;
    }

    /**
     * Logs in the client
     */
    public void login() {
        ArrayList<String> user = getLoginOrRegisterDetails();
        Request loginRequest = new Request(user.get(0),user.get(1), "login");
        ClientOutputThread clientOutputThread = new ClientOutputThread(connection, loginRequest);
        Thread thread = new Thread(clientOutputThread);
        thread.start();
    }

    /**
     * Signs up the client
     */
    public void register() {
        ArrayList<String> user = getLoginOrRegisterDetails();
        Request registerRequest = new Request(user.get(0),user.get(1), "register");
        ClientOutputThread clientOutputThread = new ClientOutputThread(connection, registerRequest);
        Thread thread = new Thread(clientOutputThread);
        thread.start();
    }

    /**
     * Get user details for login or register
     * @return ArrayList<String> of user details
     */
    public static ArrayList<String> getLoginOrRegisterDetails() {
        String username;
        String password;
        ArrayList<String> user = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your username: ");
            username = scanner.next();
            if (isValidUserNameOrPassword(username)) {
                user.add(username);
                break;
            } else {
                printInvalidUsernameOrPassword("username");
            }
        }

        while(true) {
            System.out.println("Enter your password: ");
            password = scanner.next();
            if (isValidUserNameOrPassword(password)) {
                user.add(password);
                break;
            } else {
                printInvalidUsernameOrPassword("password");
            }
        }
        return user;
    }

    /**
     * Informs the user of an invalid command entered
     * @param argument command the user entered
     */
    public static void printInvalidUsernameOrPassword(String argument) {
        System.out.println(argument + " must be at least 4 characters long");
    }

    /**
     * Checks if the user entered a good username or password
     * @param argument the value the user entered
     * @return boolean, true if valid and false is if invalid
     */
    public static boolean isValidUserNameOrPassword(String argument) {
        if(argument.length() >= 4) {
            return true;
        } else {
            return false;
        }
    }
}

