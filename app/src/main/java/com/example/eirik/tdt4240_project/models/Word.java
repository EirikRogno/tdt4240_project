package com.example.eirik.tdt4240_project.models;

public class Word {

    private String word;
    private int difficulty;

    public Word(String word, int difficulty){
        this.word = word;
        this.difficulty = difficulty;
    }

    public String getWord() {
        return word;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
