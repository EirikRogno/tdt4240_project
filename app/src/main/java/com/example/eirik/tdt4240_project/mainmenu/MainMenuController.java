package com.example.eirik.tdt4240_project.mainmenu;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.eirik.tdt4240_project.AppController;
import com.example.eirik.tdt4240_project.login.LogInActivity;
import com.example.eirik.tdt4240_project.models.Match;
import com.example.eirik.tdt4240_project.models.User;
import com.example.eirik.tdt4240_project.services.api.RetrofitFactory;
import com.example.eirik.tdt4240_project.services.api.UserApi;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainMenuController {

    private AppController appController = AppController.getInstance();
    private ArrayList<Match> matchList;
    private static MatchAdapter adapter;


    public void getAndDisplayMatches(ListView listView, Context context, final MainMenuActivity mainMenuActivity){
        String url = appController.getBaseUrl() + "match/" + appController.getUsername();
        matchList = new ArrayList<>();
        adapter = new MatchAdapter(matchList, context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Match match = matchList.get(position);

                onMatchClick(match, mainMenuActivity);
            }
        });

        JsonArrayRequest request = new JsonArrayRequest(
                url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("json_obj_req", response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Match match = new Match(obj.getString("playerOne"), obj.getString("playerTwo"), obj.getString("state"), obj.getString("id"));

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

        appController.addToRequestQueue(request);

    }

    private void onMatchClick(Match match, MainMenuActivity mainMenuActivity) {
        appController.setCurrentMatch(match);
        if(match.getState().equals("pending_invite") && !match.getplayerTwo().equals(appController.getUsername())){
            mainMenuActivity.displayDialog("Accept invitation?", match);
        }
        else if(match.getState().equals("player_one_guessing") && match.getplayerOne().equals(appController.getUsername()) ){
            mainMenuActivity.goToGuessingView();
        }
        else if(match.getState().equals("player_one_drawing") && match.getplayerOne().equals(appController.getUsername()) ){
            mainMenuActivity.goToDrawingView();
        }
        else if(match.getState().equals("player_two_guessing") && match.getplayerTwo().equals(appController.getUsername()) ){
            mainMenuActivity.goToGuessingView();
        }
        else if(match.getState().equals("player_two_drawing") && match.getplayerTwo().equals(appController.getUsername()) ){
            mainMenuActivity.goToDrawingView();
        }
    }

    public void acceptInvitation(boolean accept, final Match match, final MainMenuActivity mainMenuActivity){

        String url = appController.getBaseUrl() + "match/" + match.getId();

        if(accept){
            StringRequest request = new StringRequest(Request.Method.PUT,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("json_obj_req", response);
                            mainMenuActivity.updateMatchList();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("json_obj_req", "Error: " + error.getMessage());
                    mainMenuActivity.updateMatchList();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("state", match.getAllowedStates().get(0));
                    return params;
                }};

            appController.addToRequestQueue(request);
        }else{
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE,
                    url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("json_obj_req", response.toString());
                            mainMenuActivity.updateMatchList();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("json_obj_req", "Error: " + error.getMessage());
                    mainMenuActivity.updateMatchList();
                }
            });

            appController.addToRequestQueue(request);
        }
    }

    public void logout(final MainMenuActivity mainMenuActivity) {
        // Clear device id on the server so the device doesn't recieve notifications
        // for the user that logged out
        // Instantiate the api
        Retrofit retrofit = RetrofitFactory.getRetrofitInstance();
        UserApi userApi = retrofit.create(UserApi.class);

        // Execute the HTTP call
        Observable<String> updateResponse = userApi.updateDeviceToken(AppController.getInstance().getUsername(), "");

        // Subscribe to the result of the HTTP call
        updateResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {/*Do nothing*/ }

                    @Override
                    public void onNext(String value) {
                        Log.d("json_obj_req", "Success: " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("json_obj_req", "Error: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {/*Do nothing*/ }
                });

        // Set the username null, "logging out" the user.
        AppController.getInstance().setUsername(null);

        // Create the new intent for going to login page
        Intent loginIntent = new Intent(mainMenuActivity, LogInActivity.class);

        // Make sure the user cant use the back button to get back to the main menu without loggin in
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Go to the loginpage
        mainMenuActivity.startActivity(loginIntent);

    }

}
