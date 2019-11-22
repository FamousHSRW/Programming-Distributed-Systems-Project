package com.programming_distributed_systems_project;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;

public class ServerSocketTask implements Runnable{

    private Socket connection;  // Create Socket
    public ServerSocketTask(Socket s) {
        this.connection = s;
    }
    @Override
    public void run() {
        try {

            BufferedReader clientRequest = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Create a Request Buffer
            String requestString = clientRequest.readLine(); //Read Client request, Convert it to String
            System.out.println("Client sent : " + requestString); //Print the client request
            try {
                Thread.sleep(requestString.length()*1000); // delay the thread. Time delay = size of request string in seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            DataOutputStream serverReply = new DataOutputStream(connection.getOutputStream()); //Create a Reply Buffer
            serverReply.writeBytes("Reply : " + requestString); //write "Reply" in the outputStream
            serverReply.writeBytes("\n");
            serverReply.flush(); //Send written content to client

            serverReply.close(); //close Request Buffer
            clientRequest.close(); //close Reply Buffer

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function can be used to perform server side register user functionality
     */
    private static void register() {
    }

    /**
     * This function can be used to perform server side login user functionality
     */
    private static void login() {

    }

    /**
     * This function can be used to create a team
     */
    private static void createTeam() {

    }

    /**
     * This function can be used to send all replies to the user
     * @param reply
     * @param operation
     */
    private static void notifyClient(String reply, String operation) {

    }
}
