package com.example.eirik.tdt4240_project.login;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.eirik.tdt4240_project.AppController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogInController {

    AppController appController = AppController.getInstance();

    public void getUser(String userID, final LogInActivity logInActivity){
        Log.d("userId:", userID);
        String url = appController.getBaseUrl() + "user/" + userID;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("json_obj_req", response.toString());
                        logInActivity.goToMainMenu();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("json_obj_req", "Error: " + error.getMessage());
                        logInActivity.displayUserNotFound();
                    }
                });

        appController.addToRequestQueue(request);

    }

    public void createUser(final String userID){

        String url = appController.getBaseUrl() + "user/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("json_obj_req", response.toString());
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
                params.put("username", userID);

                return params;
        }};

        appController.addToRequestQueue(request);

    }



}
