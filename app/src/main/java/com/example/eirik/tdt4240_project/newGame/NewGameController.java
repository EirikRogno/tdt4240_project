package com.example.eirik.tdt4240_project.newGame;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.eirik.tdt4240_project.AppController;

import org.json.JSONObject;

import java.util.HashMap;

public class NewGameController {

    private AppController appController = AppController.getInstance();


    public void createNewGame(final String userID, final NewGameActivity newGameActivity) {
        if(userID.equals(appController.getUsername())){
            newGameActivity.displayMessage("Cannot invite yourself!");
        }else {

            String url = appController.getBaseUrl() + "user/" + userID;


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("json_obj_req", response.toString());
                            if (response.equals("")) {
                                newGameActivity.displayMessage("User does not exist!");
                            } else {
                                postNewGame(userID, appController.getUsername(), newGameActivity);
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("json_obj_req", "Error: " + error.getMessage());
                    newGameActivity.displayMessage("User does not exist!");
                }
            });
            appController.addToRequestQueue(request);
        }

    }

    private void postNewGame(final String user1, final String user2, final NewGameActivity newGameActivity){

        String url = appController.getBaseUrl() + "match/";
        Log.d(user1,user2);
        HashMap<String, String> params = new HashMap<>();
        params.put("playerOne", user1);
        params.put("playerTwo", user2);
        params.put("state", "pending_invite");

        JsonObjectRequest request = new JsonObjectRequest(
                url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("json_obj_req", response.toString());
                        newGameActivity.returnToMainMenu();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("json_obj_req", "Error: " + error.getMessage());
            }
        });

        appController.addToRequestQueue(request);
    }


}
