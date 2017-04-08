package com.example.eirik.tdt4240_project.mainmenu;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.eirik.tdt4240_project.AppController;
import com.example.eirik.tdt4240_project.models.Match;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainMenuController {

    private AppController appController = AppController.getInstance();
    private ArrayList<Match> matchList = new ArrayList<>();
    private static MatchAdapter adapter;


    public void getAndDisplayMatches(ListView listView, Context context){
        String url = appController.getBaseUrl() + "match/" + appController.getUsername();

        adapter = new MatchAdapter(matchList, context);
        listView.setAdapter(adapter);

        JsonArrayRequest request = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("json_obj_req", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Match match = new Match(obj.getString("playerOne"), obj.getString("playerTwo"), obj.getString("state"));

                                matchList.add(match);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("json_obj_req", "Error: " + error.getMessage());
            }
        });

        Log.d("matches", matchList.toString());

        appController.addToRequestQueue(request);

    }



}
