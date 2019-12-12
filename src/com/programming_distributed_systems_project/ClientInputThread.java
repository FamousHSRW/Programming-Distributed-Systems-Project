package com.programming_distributed_systems_project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientInputThread implements Runnable {
    private Socket connection;
    private ObjectInputStream inputStream;
    private UserInterface userInterface;

    public ClientInputThread(Socket connection, UserInterface userInterface) {
        this.connection = connection;
        this.userInterface = userInterface;
    }

    @Override
    public void run() {
        try {
            handleReply();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handle all replies sent from server to client
     * TODO: handle all cases
     */
    public void handleReply() {
        while(true) {
            try {
                inputStream = new ObjectInputStream(connection.getInputStream());
                Reply reply = (Reply) inputStream.readObject(); //Read Server Reply
                String nextOperation = reply.nextOperation();
                Object replyData = reply.getReplyData();
                System.out.println(reply.getResponse());
                switch (nextOperation) {
                    case "choose team": {
                        User user = reply.getUser();
                        userInterface.chooseTeamInterface(user, replyData);
                        break;
                    }
                    case "wait": {
                        System.out.println("Wait for the team to get full");
                        this.handleReply();
                        break;
                    }
                    case "retry": {
                        userInterface.loggedOutInterface();
                        break;
                    }
                    case "login": {
                        System.out.println("You are now successfully registered, login to activate your account");
                        userInterface.loggedOutInterface();
                        break;
                    }
                    case "choose character": {
                        User user = reply.getUser();
                        userInterface.chooseCharacterInterface(user, replyData);
                        break;
                    }
                    case "chosen character": {
                        Team team = (Team) reply.getReplyData();
                        // TODO: what to do here probably ask the user if he or she wishes to see results
    //                    System.out.println(team.printCharacterSelection());
                    }
                }
            } catch(ClassNotFoundException | IOException e ) {
                System.out.println("Lost connection to server, terminating...");
//                e.printStackTrace();
                break;
            }
        }

    }

}
