package com.programming_distributed_systems_project;

import java.util.Properties;

public class Reader {
    private int ranking;
    public String name;
    public Reader (String name){
        this.name = name;
    }
    public int getRanking() {
        return ranking;
    }
    public void setRanking() {
        int max =5;
        int min = 1;
        int index = (int)(Math.random() * ((max - 1) + 1)) + min;
        ranking = index;
    }
    public static void main(String[] args) {
        Reader testReader = new Reader("Mark");
        System.out.println("Test for ranking class");
        System.out.println("Ranking at the start " + testReader.getRanking());
        testReader.setRanking();
        System.out.println("Ranking after one set ranking call " + testReader.getRanking());
    }
    public String toString() {
        return "This reader's name is " + this.name;
    }
}