package com.example.eirik.tdt4240_project.guessing;

//GET:/drawing/{matchID}

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.eirik.tdt4240_project.AppController;
import com.example.eirik.tdt4240_project.models.Match;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuessingController {

    AppController appController = AppController.getInstance();

    public String getWord() {
        return appController.getCurrentWord();
    }

    public void correctGuess(String guess, GuessingActivity guessingActivity){
        if (guess.toLowerCase().equals(getWord().toLowerCase())){
            guessingActivity.displayMessage("Correct!", "You guessed correct! Wooohooo, you're amazing!", "OK");
            final Match match = appController.getCurrentMatch();

            List<String> states = match.getAllowedStates();

            final String newState = states.indexOf(match.getState()) >= 3 ? states.get(0) : states.get(states.indexOf(match.getState()) + 1 );

            Log.d("newstate", newState);

            String matchUrl = appController.getBaseUrl() + "match/" + match.getId();

            StringRequest stateRequest = new StringRequest(Request.Method.PUT,
                    matchUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("json_obj_req", response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("json_obj_req", "Error: " + error.getMessage());
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("state", newState);
                    return params;
                }};

            appController.addToRequestQueue(stateRequest);
        }else {
            guessingActivity.displayMessage("Wrong word!", "You guessed the wrong word, try again.", "Try again");
        }
    }



}
