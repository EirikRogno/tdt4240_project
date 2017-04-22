package com.example.eirik.tdt4240_project.login;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.eirik.tdt4240_project.AppController;
import com.example.eirik.tdt4240_project.models.User;
import com.example.eirik.tdt4240_project.services.api.RetrofitFactory;
import com.example.eirik.tdt4240_project.services.api.UserApi;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LogInController {

    AppController appController = AppController.getInstance();

    public void getUser(final String userID, final LogInActivity logInActivity){
        // Instantiate the api
        Retrofit retrofit = RetrofitFactory.getRetrofitInstance();
        UserApi userApi = retrofit.create(UserApi.class);

        // Get the device token used for push notifications
        String deviceToken = FirebaseInstanceId.getInstance().getToken();

        // Execute the HTTP call
        Observable<User> newUserResult = userApi.loginUser(userID, deviceToken);

        // Subscribe to the result of the HTTP call
        newUserResult.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {/*Do nothing*/ }

                    @Override
                    public void onNext(User value) {
                        // This launches when we recive a
                        // response that can be deserialized to a user
                        Log.d("json_obj_req", value.toString());
                        appController.setUsername(value.getUsername());
                        logInActivity.goToMainMenu();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("json_obj_req", "Error: " + e.getMessage());
                        logInActivity.displayMessage("Username does not exist!");
                    }

                    @Override
                    public void onComplete() {/*Do nothing*/ }
                });

    }

    public void createUser(final String userID, final LogInActivity logInActivity){

        // Instantiate the api
        Retrofit retrofit = RetrofitFactory.getRetrofitInstance();
        UserApi userApi = retrofit.create(UserApi.class);

        // Get the device token used for push notifications
        String deviceToken = FirebaseInstanceId.getInstance().getToken();

        // Execute the HTTP call
        Observable<User> newUserResult = userApi.createUser(userID, deviceToken);

        // Subscribe to the result of the HTTP call
        newUserResult.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {/*Do nothing*/ }

                    @Override
                    public void onNext(User value) {
                        Log.d("json_obj_req", value.toString());
                        if(value.getUsername().equals("")){
                            logInActivity.displayMessage("Username taken!");
                        }else {
                            appController.setUsername(userID);
                            logInActivity.goToMainMenu();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("json_obj_req", "Error: " + e.getMessage());
                        logInActivity.displayMessage("Username taken!");
                    }

                    @Override
                    public void onComplete() {/*Do nothing*/ }
                });

    }



}
