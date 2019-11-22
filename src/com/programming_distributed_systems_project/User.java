package com.programming_distributed_systems_project;

import java.util.Scanner;

public class User {
    private static String username;
    private  static String password;
    public static void login() {
        getUser();
    }

    public static void register() {
        getUser();
    }

    public static void getUser(){

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your username: ");
            username = scanner.next();
            if (isValidUserNameOrPassword(username)) {
                break;
            } else {
                printInvalidUsernameOrPassword("username");
            }
        }

        while(true) {
            System.out.println("Enter your password: ");
            password = scanner.next();
            if (isValidUserNameOrPassword(password)) {
                // server should do login
                break;
            } else {
                printInvalidUsernameOrPassword("password");
            }
        }
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
