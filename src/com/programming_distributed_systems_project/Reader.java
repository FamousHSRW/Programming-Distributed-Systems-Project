package com.programming_distributed_systems_project;

import java.io.Serializable;

/**
 * This class represents each reader in a team stored on the server
 * Basically tells java properties and methods availabe to a reader
 */
public class Reader implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ranking;
    public String name;

    public Reader (String name){
        this.name = name;
    }

    public int getRanking() {
        return ranking;
    }

    /**
     * Gives the reader a random ranking between 1 - 5
     */
    public void setRanking() {
        int max =5;
        int min = 1;
        int index = (int)(Math.random() * ((max - 1) + 1)) + min;
        ranking = index;
    }

    // TODO: Remove this test before submission
    public static void main(String[] args) {
        Reader testReader = new Reader("Mark");
        System.out.println("Test for ranking class");
        System.out.println("Ranking at the start " + testReader.getRanking());
        testReader.setRanking();
        System.out.println("Ranking after one set ranking call " + testReader.getRanking());
    }

    // TODO: remove this test before submission
    public String toString() {
        return "This reader's name is " + this.name;
    }
}
