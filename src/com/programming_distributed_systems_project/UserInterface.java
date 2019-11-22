package com.programming_distributed_systems_project;
import java.util.Scanner;

/**
 * This class handles all stuff which involves a user interface
 * This is used mainly as main menu and fallback in case the user leaves all application flow
 */
public class UserInterface {

    /**
     * This is the main entry point to the user interface
     * It will determine the user interface to be shown depending on if the user is loggedIn or not
     */
    public static void showInterface() {
        printExitInfo();
        System.out.println("What do you want to do? Enter number of command");
        // normally ask server here if the user is logged in or not
        loggedOutInterface();
    }

    /**
     * This will print out different steps for a logged in user to follow
     * It will automatically load other classes and move users to new application flows
     */
    public static void loggedInInterface() {
    }

    /**
     * hip r&b pop
     */
    /**
     * This will print out different steps for a logged out user to follow
     * It will automatically load other classes and move users to new application flows
     */
    public static void loggedOutInterface() {
        while(true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            if(isQuit(input)) {
                printThanks();
                break;
            } else {
                try {
                    int command = new Integer(input);
                    Client client = new Client();
                    switch (command) {
                        case 1:
                            client.login();
                            break;
                        case 2:
                            client.register();
                            break;
                        default:
                            printUnknownCommand();
                            break;
                    }
                } catch (NumberFormatException e) {
                    printUnknownCommand();
                }
            }
        }

    }

    /**
     * Checks if the user entered q to quit the application
     * @param command
     * @return true if the user entered q or false otherwise
     */
    public static boolean isQuit(String command) {
        return "q".equals(command) ? true : false;
    }

    /**
     * Tell the user he entered a command which is not among the current valid list of commands
     */
    public static void printUnknownCommand() {
        System.out.println("That is not a valid command, please check command list");
        printExitInfo();
    }

    /**
     * Show an info message on what the user should do if he wants to quit the application
     */
    public static void printExitInfo() {
        System.out.println("INFO: Enter `q` to quit");
    }

    /**
     * Print a simple thanks message on terminating the application
     */
    public static void printThanks() {
        System.out.println("Thanks for playing...");
    }
}
