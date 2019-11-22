package com.programming_distributed_systems_project;

import java.util.Scanner;

public class User {
    private static String username;
    private  static String password;
    private  static Scanner scanner = new Scanner(System.in);
    private static UserInterface userInterface = new UserInterface();

    public static void login() {
        getUserInput("username");
        getUserInput("password");
        // send login details to server
    }

    public static void joinTeam() {

    }

    public  static void chooseCharacter() {

    }

    public static void getUserInput(String argument) {
        String input;
        while(true) {
            System.out.println("Enter your " + argument + ": ");
            input = scanner.next();
            if (userInterface.isQuit(input)) {
                break;
            } else if (isValidUserNameOrPassword(input)) {
                switch (argument) {
                    case "username":
                        username = input;
                        break;
                    case "password":
                        password = input;
                        break;
                }
            } else {
                printInvalidUsernameOrPassword(argument);
            }
        }
    }
    public static void printInvalidUsernameOrPassword(String argument) {
        System.out.println(argument + " must be at least 4 characters long");
    }
    public static void register() {
        System.out.println("Implementation of register coming soon!!");
    }
    public static boolean isValidUserNameOrPassword(String argument) {
        if(argument.length() >= 4) {
            return true;
        } else {
            return false;
        }
    }
}
