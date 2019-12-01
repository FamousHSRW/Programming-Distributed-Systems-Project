package com.programming_distributed_systems_project;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class describes how a theater script should look like
 */
public class Script {
    private ArrayList<Character> characters;
    private int difficulty;

    public Script (int difficulty) {
        this.difficulty = difficulty;
        generateCharacters();
    }

    public int getDifficulty() {
        return difficulty;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * Adds random characters to script
     */
    public void generateCharacters() {
        ArrayList<Character> characters = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            characters.add(getRandomCharacter());
        }
        this.characters = characters;
    }

    /**
     * Returns a random character between [a-z]
     * @return random character
     */
    private char getRandomCharacter() {
        Random r = new Random();
        return (char)(r.nextInt(26) + 'a');
    }

    /**
     * TODO: remove test before submission
     */
    public static void main(String[] args) {
        Script script = new Script(2);
        System.out.println(script.getRandomCharacter());
        System.out.println(script.getCharacters().toString());
    }
}
