package com.programming_distributed_systems_project;

import java.io.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a single thread which will be given to each client connected to the server at a particular time
 * All request and replies made between the client and server are handled here on the server side
 */
public class ServerSocketTask implements Runnable{
    private static HashMap<Integer, User> users = new HashMap<>();
    private static HashMap<Integer, Team> teams = new HashMap<>();
    private Socket connection;  // Create Socket
    private ObjectInputStream clientRequest;
    private ObjectOutputStream serverReply;

    public ServerSocketTask(Socket s) {
        this.connection = s;
    }

    @Override
    public void run() {
            try {
                System.out.println("Socket task");
                clientRequest = new ObjectInputStream(connection.getInputStream()); //Create a Request Buffer
                Request request = (Request) clientRequest.readObject(); //Read Client request, Convert it to String
                System.out.println("Client sent : " + request.toString()); //Print the client request
                handleRequest(request);
//            clientRequest.close(); //close Reply Buffer
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("connection closed");
                e.printStackTrace();
                this.killSocketTask();
            }

    }

    /**
     * Kill this socket task if the user disconnects
     * FIXME: This functions needs an update to actually kill the socket task i.e. remove this class from memory when the user disconnects
     */
    private void killSocketTask() {
        try {
            if(clientRequest != null) clientRequest.close();
            if(serverReply != null) serverReply.close();
            if(connection != null) connection.close();
        } catch (IOException e) {
            System.out.println("Couldn't kill server task");
            e.printStackTrace();
        }

    }

    /**
     * TODO: add and handle the other cases (choose character, ...)
     * Handles all request made from client to server
     * @param request
     * @throws IOException
     */
    private void handleRequest(Request request) throws IOException {
        String operation = request.getOperation();
        switch (operation) {
            case "register":
                this.register(request);
                break;
            case "login":
                this.login(request);
                break;
            case "join team":
                this.joinTeam(request);
                break;
        }
    }

    /**
     * This function can be used to perform server side register user functionality
     */
    private void register(Request request) throws IOException {
        int userId = users.size() + 1;
        String reqUsername = request.getUsername();
        String reqPassword = request.getPassword();
        boolean freeUserName = true;
        for(int i =  1; i <= users.size(); i++) {
            if(users.isEmpty() || request == null) {
                break;
            }
            User _user = users.get(i);
            String userName = _user.getUsername();
            if(userName.equals(reqUsername)) {
               freeUserName = false;
               break;
            }
        }
        if (freeUserName) {
            User user = new User(reqUsername, reqPassword, userId);
            users.put(userId, user);
            this.notifyClient("Successfully registered", null, null, "login");
        } else {
            this.notifyClient("The username is taken", null, null, "retry");
        }
    }

    /**
     * This function can be used to perform server side login user functionality
     */
    private void login(Request request) throws IOException {
        User user = null;
        String reqPassword = request.getPassword();
        String reqUsername = request.getUsername();
        for(int i =  1; i <= users.size(); i++) {
            if(users.isEmpty() || request == null) {
                break;
            }
            User _user = users.get(i);
            String userPassword = _user.getPassword();
            String userName = _user.getUsername();
            if(userName.equals(reqUsername) && userPassword.equals(reqPassword)) {
                user = _user;
                break;
            }
        }
        if(user != null) {
            if(!user.getHasTeam()) {
                ArrayList<Integer> availableTeams = getAvailableTeams();
                if(availableTeams.size() < 1) {
                    Team newTeam = createTeam();
                    availableTeams.add(newTeam.getId());
                    this.addReaderToTeam(user, newTeam);
                }
                this.notifyClient("Successfully loggedIn", user, availableTeams,  "choose team");
            } else {
                this.notifyClient("Successfully loggedIn", user, null, "wait");
            }
        } else {
            this.notifyClient("User details incorrect", null, null, "retry");
        }
    }

    /**
     * Creates a new team
     */
    private static Team createTeam() {
        int numberOfTeams = teams.size();
        int teamId = numberOfTeams + 1;
        String newTeamName = "team" + String.valueOf(teamId);
        Team newTeam = new Team(teamId, newTeamName);
        teams.put(teamId, newTeam);
        return newTeam;
    }

    /**
     * Adds a reader to a specific team
     * @param user
     * @param team
     * @return team which the user was added to
     */
    private static Team addReaderToTeam(User user, Team team) {
        Reader reader = new Reader(user.getUsername());
        user.setHasTeam(true);
        team.setReader(reader);
        return team;
    }

    /**
     * Adds a user to a team
     * TODO: if a user joins a team i.e the team was not full, check if the team, after adding the user becomes full
     * TODO: if the team is full after adding the user notify clients to begin selecting characters
     * @param request
     */
    private void joinTeam(Request request) throws IOException {
        int teamId = request.getTeamId();
        int userId = request.getUserId();
        User user = users.get(userId);
        Team team = teams.get(teamId);
        Reader reader = new Reader(user.getUsername());
        if(!team.isFull()) {
            team.setReader(reader);
            this.notifyClient("Successfully joined team", user, team, "choose character");
        } else {
            ArrayList<Integer> availableTeams = getAvailableTeams();
            if(availableTeams.size() < 1) {
                Team newTeam = createTeam();
                availableTeams.add(newTeam.getId());
                this.addReaderToTeam(user, newTeam);
            }
            this.notifyClient("Team"+ teamId + " unfortnately is full", user, availableTeams, "choose team");
        }
    }

    /**
     * Returns teams available for a user to join
     * TODO: make sure the team is suitable for the user to join (take a look at description on slides)
     * @return
     */
    private ArrayList<Integer> getAvailableTeams() {
        ArrayList<Integer> availableTeams = new ArrayList<>();
        teams.forEach((k, v) -> {
            if(!v.isFull()) {
                availableTeams.add(v.getId());
            }
        });
        return availableTeams;
    }

    /**
     * TODO: add implementation (see slides)
     */
    private void getCharacterScripts() {

    }

    /**
     * Sends all replies from server to client
     * @param response
     */
    private void notifyClient(String response, User user, Object responseData, String nextOperation) throws IOException {
        Reply reply = new Reply(response, user, responseData, nextOperation);
        serverReply = new ObjectOutputStream(connection.getOutputStream()); //Create a Reply Buffer
        serverReply.writeObject(reply); //write "Reply" in the outputStream
        serverReply.flush(); //Send written content to client
//        serverReply.close(); //close Request Buffer
    }
}
