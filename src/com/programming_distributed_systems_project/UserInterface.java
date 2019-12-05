package com.programming_distributed_systems_project;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles all stuff which involves a user interface
 * This is used mainly as main menu and fallback in case the suddenly user leaves all application flow
 */
public class UserInterface {
    private static Scanner scanner = new Scanner(System.in);
    private Client client;
    private User user = null;

    public UserInterface(Client client) {
        this.client = client;
    }

    /**
     * This constructor is only called when the user is logged in
     * Because we need a user to access non static properties of this class
     * @param user
     */
    public UserInterface(Client client, User user) {
        this.user = user;
        this.client = client;
    }


    /**
     * Interface shown when the user is about to choose a team
     * FIXME: might not work as expected (see slides)
     * @param data
     */
    public void chooseTeamInterface(Object data) {
        data = (ArrayList<Integer>) data;
        System.out.println("Choose a team from one of the teams below: ");
        for (int i = 1; i <= ((ArrayList) data).size(); i++) {
            System.out.println(i+". team"+ ((ArrayList) data).get(i - 1));
        }
        while(true) {
            try {
                int teamSelection = new Integer(scanner.nextLine());
                if(teamSelection > ((ArrayList) data).size()) {
                    throw new NumberFormatException();
                } else {
                    Request request = new Request((int) ((ArrayList) data).get(teamSelection - 1), user.getUserId(), "join team");
                    client.sendRequest(request);
                }
            } catch(NumberFormatException e) {
                printUnknownCommand();
            }
        }
    }

    /**
     * TODO: add show results implementation
     * Shows the user a list of results of all teams based on the previous gameplay
     */
    public void showResults(Object data) {
        System.out.println("The result of this game round is:");

    }

    /**
     * TODO: add choose character implementation
     * Shows the user an interface to pick a character
     * Gets the character picked by the user and sends as a choose character request to the server
     * @param data
     */
    public void chooseCharacterInterface(Object data) {
        System.out.println("Choose a character from the list below: ");
        Script script = ((Team) data).getScript();
        ArrayList<Character> characters = script.getCharacters();
        for(int i = 0; i < characters.size(); i++) {
            System.out.println(i + ". " + characters.get(i));
        }
        String selection = scanner.next();
        try {
            int command = new Integer(selection);
            if(command > characters.size()) throw new NumberFormatException();
            else {
                Request request = new Request(user.getUserId() ,selection);
                client.sendRequest(request);
            }
        } catch (NumberFormatException e) {
            printUnknownCommand();
        }
        //TODO: take user selection, check to make sure it's valid, then send to server
        // TODO: See choose team implementation
    }

    /**
     * TODO: application doesn't quit sometimes when user enters q
     * This will print out different steps for a logged out user to follow
     * It will automatically load other classes and move users to new application flows
     */
    public static void loggedOutInterface(Client client) {
        while(true) {
            printExitInfo();
            System.out.println("What do you want to do? Enter number of command");
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
                    printExitInfo();
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
        return "q".equals(command);
    }

    /**
     * Tell the user he entered a command which is not among the current valid list of commands
     */
    public static void printUnknownCommand() {
        System.out.println("That is not a valid command, please check command list");
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
