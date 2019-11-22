package com.programming_distributed_systems_project;

import java.io.*;

import java.net.Socket;
import java.util.HashMap;

public class ServerSocketTask implements Runnable{
    private static HashMap<Integer, User> users = new HashMap<>();
    private Socket connection;  // Create Socket
    public ServerSocketTask(Socket s) {
        this.connection = s;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream clientRequest = new ObjectInputStream(connection.getInputStream()); //Create a Request Buffer
            Request request = (Request) clientRequest.readObject(); //Read Client request, Convert it to String
            System.out.println("Client sent : " + request.toString()); //Print the client request
            handleRequest(request);
            clientRequest.close(); //close Reply Buffer
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void handleRequest(Request request) throws IOException {
        String operation = request.getOperation();
        switch (operation) {
            case "register":
                this.register(request);
                break;
            case "login":
                this.login(request);
                break;
        }
    }
    /**
     * This function can be used to perform server side register user functionality
     */
    private void register(Request request) throws IOException {
        int userId = users.size() + 1;
        User user = new User(request.getUsername(), request.getPassword(), userId);
        users.put(userId, user);
        this.notifyClient("Successfully registered");
    }

    /**
     * This function can be used to perform server side login user functionality
     */
    private void login(Request request) throws IOException {
        boolean loggedIn = false;
        String reqPassword = request.getPassword();
        String reqUsername = request.getUsername();
        for(int i =  1; i <= users.size(); i++) {
            if(users.isEmpty() || request == null) {
                break;
            }
            User user = users.get(i);
            String userPassword = user.getPassword();
            String userName = user.getUsername();
            if(userName.equals(reqUsername) && userPassword.equals(reqPassword)) {
                loggedIn = true;
                break;
            }
        }
        if(loggedIn) {
            this.notifyClient("Successfully loggedIn");
        } else {
            this.notifyClient("User details incorrect");
        }
    }

    /**
     * This function can be used to create a team
     */
    private static void createTeam() {

    }

    /**
     * This function can be used to send all replies to the user
     * @param response
     */
    private void notifyClient(String response) throws IOException {
        Reply reply = new Reply(response);
        ObjectOutputStream serverReply = new ObjectOutputStream(connection.getOutputStream()); //Create a Reply Buffer
        serverReply.writeObject(reply); //write "Reply" in the outputStream
        serverReply.flush(); //Send written content to client
        serverReply.close(); //close Request Buffer
    }
}
