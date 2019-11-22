package com.programming_distributed_systems_project;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private Socket connection;
    private  static Scanner scanner = new Scanner(System.in);
    private static UserInterface userInterface = new UserInterface();
    private static ObjectOutputStream outputStream; //Create a Request Buffer

    public Client() {
        connect();
    }
    private void connect() {
        int port = 1234; // initialize port number
        String ip = "localhost"; // localhost ip address = 127.0.0.1
        try {
            this.connection = new Socket(ip, port); //Create a Client Socket for "localhost" address and port
        } catch(IOException e) {
            System.out.println("You have no connection to the server");
            e.printStackTrace();
        }
    }

    public void login() {
        ArrayList<String> user = getLoginOrRegisterDetails();
        Request loginRequest = new Request(user.get(0),user.get(1), "login");
        this.sendRequest(loginRequest);
    }

    public void register() {
        ArrayList<String> user = getLoginOrRegisterDetails();
        Request registerRequest = new Request(user.get(0),user.get(1), "register");
        this.sendRequest(registerRequest);
    }

    public void sendRequest(Request request) {
        try {
            System.out.println("Connected...");
            outputStream = new ObjectOutputStream(this.connection.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();

            ObjectInputStream rd = new ObjectInputStream(connection.getInputStream()); //Create a Reply Object Buffer
            Reply serverReply = (Reply) rd.readObject(); //Read Server Reply
            System.out.println(serverReply.getResponse());

            outputStream.close();
        } catch(IOException | ClassNotFoundException e) {
            System.out.println("Couldn't connect to the server...");
        }
    }

    public ArrayList<String> getLoginOrRegisterDetails(){
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

    public static void printInvalidUsernameOrPassword(String argument) {
        System.out.println(argument + " must be at least 4 characters long");
    }

    public static boolean isValidUserNameOrPassword(String argument) {
        if(argument.length() >= 4) {
            return true;
        } else {
            return false;
        }
    }
}

