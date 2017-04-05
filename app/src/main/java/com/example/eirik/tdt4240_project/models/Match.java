package com.example.eirik.tdt4240_project.models;

import java.util.Arrays;
import java.util.List;

public class Match {
    private String playerOne;
    private String playerTwo;
    private int score;
    private String state;

    private List<String> allowedStates = Arrays.asList("playerOne_drawing", "player_two_drawing", "playerOne_guessing", "player_two_guessing", "pending_invite");

    public Match(String playerOne, String player_two){
        this.playerOne = playerOne;
        this.playerTwo = player_two;
        this.state = allowedStates.get(4);
    }


    public String getplayerOne() {
        return playerOne;
    }

    public String getplayerTwo() {
        return playerTwo;
    }

    public String getState() {
        return state;
    }
}
