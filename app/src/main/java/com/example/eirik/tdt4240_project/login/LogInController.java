package com.example.eirik.tdt4240_project.login;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.eirik.tdt4240_project.AppController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogInController {

    AppController appController = AppController.getInstance();

    public void getUser(final String userID, final LogInActivity logInActivity){
        String url = appController.getBaseUrl() + "user/" + userID;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("json_obj_req", response.toString());
                        appController.setUsername(userID);
                        logInActivity.goToMainMenu();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("json_obj_req", "Error: " + error.getMessage());
                        logInActivity.displayMessage("Username does not exist!");
                    }
                });

        appController.addToRequestQueue(request);

    }

    public void createUser(final String userID, final LogInActivity logInActivity){

        String url = appController.getBaseUrl() + "user/";

        StringRequest request = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("json_obj_req", response.toString());
                        appController.setUsername(userID);
                        logInActivity.goToMainMenu();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("json_obj_req", "Error: " + error.getMessage());
                logInActivity.displayMessage("Username taken!");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", userID);
                return params;
        }};

        appController.addToRequestQueue(request);

    }



}
