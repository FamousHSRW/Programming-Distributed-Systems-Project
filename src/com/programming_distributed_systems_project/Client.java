package com.programming_distributed_systems_project;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private Socket connection;
    private  static Scanner scanner = new Scanner(System.in);
    private  static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;
    private User user = null;

    public Client() {
    }

    /**
     * Connects the client to the server
     */
    private void connect() {
        int port = 1234; // initialize port number
        String ip = "localhost"; // localhost ip address = 127.0.0.1
        try {
            connection = new Socket(ip, port); //Create a Client Socket for "localhost" address and port
        } catch(IOException e) {
            System.out.println("You have no connection to the server");
            e.printStackTrace();
        }
    }

    /**
     * Logs in the client
     */
    public void login() {
        ArrayList<String> user = getLoginOrRegisterDetails();
        Request loginRequest = new Request(user.get(0),user.get(1), "login");
        sendRequest(loginRequest);
    }

    /**
     * Signs up the client
     */
    public void register() {
        ArrayList<String> user = getLoginOrRegisterDetails();
        Request registerRequest = new Request(user.get(0),user.get(1), "register");
        this.sendRequest(registerRequest);
    }

    /**
     * Handle all request made by client to server
     * @param request
     */
    public void sendRequest(Request request) {
        connect();
        try {
            System.out.println(">>>>>>>>>>>>>>>>>>");
            outputStream = new ObjectOutputStream(connection.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();
            handleReply();
        } catch (IOException e) {
            System.out.println("Couldn't connect to the server...");
            e.printStackTrace();
        }
    }

    /**
     * Handle all replies sent from server to client
     */
    public void handleReply() {
        try {
            inputStream = new ObjectInputStream(connection.getInputStream());
            Reply reply = (Reply) inputStream.readObject(); //Read Server Reply
            String nextOperation = reply.nextOperation();
            Object replyData = reply.getReplyData();
            System.out.println(reply.getResponse());
            switch (nextOperation) {
                case "choose team": {
                    User user = reply.getUser();
                    UserInterface userInterface = new UserInterface(this, user);
                    if(((ArrayList) replyData).size() == 1 && user.getHasTeam()) {
                        System.out.println("You have been automatically added to team" + ((ArrayList) replyData).get(0));
                        System.out.println("Wait a moment for your team to get complete");
                        this.handleReply();
                    } else {
                        userInterface.chooseTeamInterface(replyData);
                    }
                    break;
                }
                case "wait": {
                    System.out.println("Wait a moment for your team to get complete");
                    this.handleReply();
                    break;
                }
                case "retry": {
                    UserInterface.loggedOutInterface(this);
                    break;
                }
                case "login": {
                    System.out.println("You are now successfully registered, login to activate your account");
                    UserInterface.loggedOutInterface(this);
                    break;
                }
                case "choose character": {
                    User user = reply.getUser();
                    UserInterface userInterface = new UserInterface(this, user);
                    userInterface.chooseCharacterInterface(replyData);
                    break;
                }
            }
        } catch(ClassNotFoundException | IOException e ) {
            System.out.println("Couldn't connect to the server...");
            e.printStackTrace();
        }

    }

    /**
     * Get user details for login or register
     * @return ArrayList<String> of user details
     */
    public static ArrayList<String> getLoginOrRegisterDetails(){
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

