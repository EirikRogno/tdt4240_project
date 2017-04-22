package com.example.eirik.tdt4240_project.guessing;

//GET:/drawing/{matchID}

import com.example.eirik.tdt4240_project.AppController;

public class GuessingController {

    AppController appController = AppController.getInstance();


    public String getWord(){
        // get word from backend - matchID
        // GET:/drawing/{matchID} Returns latest drawing for given match => Word: String

        return "Banan";
    }

    public void correctGuess(String guess, GuessingActivity guessingActivity){
        if (guess.equals(getWord())){
            guessingActivity.displayMessage("You guessed correct! Wooohooo, you're amazing!");

            // TODO: change to the next state, new view
            // guessingActivity.goToNextState();
        }else {
            guessingActivity.displayMessage("You guessed the wrong word, try again.");
        }
    }



}
