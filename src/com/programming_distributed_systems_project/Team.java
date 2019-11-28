package com.programming_distributed_systems_project;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This class represents each team in teams stored on the server
 * Basically tells java properties and methods availabe to a team
 */
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;

    private HashMap<Integer, Reader> readers = new HashMap<Integer, Reader>();
    private int maximumNumberOfReaders = 3;
    private String name;
    private int id;


    public Team(int id, String teamName) {
        this.id = id;
        name = teamName;
    }

    public static void main(String[] args) {
        Team team1 = new Team(1, "team1");
        Reader tom = new Reader("tom");
        Reader mark = new Reader("mark");
        Reader james = new Reader("James");
        Reader niko = new Reader("niko");
        team1.setReader(james);
        team1.setReader(tom);
        team1.setReader(mark);
        if(team1.setReader(niko)) {
            System.out.println("Added niko");
        } else {
            // reply
            System.out.println("Couldn't add niko");
        }


        System.out.println("Team 1 readers" + team1.getReaders());
    }

    /**
     *
     * @param reader
     * @return true if was able to add reader or false if not able to add reader
     */
    public boolean setReader(Reader reader) {
        int numberOfReaders  = readers.size();
        if(numberOfReaders < maximumNumberOfReaders) {
            readers.put(numberOfReaders++, reader);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Add get all readers in the team
     * @return
     */
    public HashMap<Integer, Reader> getReaders() {
        return readers;
    }

    public boolean isFull() {
        return readers.size() >= 3 ? true : false;
    }

    /**
     * Returns team name
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns team id
     * @return id
     */
    public int getId() {
        return this.id;
    }
}
