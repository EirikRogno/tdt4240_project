package com.example.eirik.tdt4240_project.services;

import android.util.Log;

import com.example.eirik.tdt4240_project.AppController;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by sindr on 21.04.2017.
 */

public class InstanceIdService extends FirebaseInstanceIdService {



    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(AppController.TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        // sendRegistrationToServer(refreshedToken);
    }
}
