package com.programming_distributed_systems_project;

import java.io.*;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) throws UnknownHostException, IOException {
        Scanner scanner = new Scanner(System.in); // Create scanner to allow keyboard input
        while(true) {
            System.out.print("Enter client name :   \n");
            String client = scanner.nextLine(); // Waiting for keyboard input
            System.out.print("Enter operation name :   \n");
            String operation = scanner.nextLine(); // Waiting for keyboard input
            Request request = new Request(client, operation); //Create a Request
            if ("q".equals(client) || "q".equals(operation)) {
                System.out.println("Exit!"); // if keyboard input equal to ´q´ close client process
                break;
            }
        }
    }
}

