package com.programming_distributed_systems_project;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class ClientThread implements Callable<Integer> {
    private int maxValue;
    private Client client;
    private User user;
    private ArrayList<Character> characters;
    public ClientThread(int maxValue, Client client, User user, ArrayList<Character> characters) {
        this.maxValue = maxValue;
        this.client = client;
        this.user = user;
        this.characters = characters;
    }
    @Override
    public Integer call() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String selection = scanner.nextLine();
            try {
                Integer userInput = Integer.valueOf(selection);
                System.out.println(selection);
                Request request = new Request(user.getUserId(), characters.get(userInput - 1), "select character");
                client.sendRequest(request);
                if(userInput > maxValue) throw new NumberFormatException();
                else return userInput;
            } catch (NumberFormatException e) {
                UserInterface.printUnknownCommand();
            }
        }

    }

}
