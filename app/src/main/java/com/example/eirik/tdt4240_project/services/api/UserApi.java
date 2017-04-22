package com.example.eirik.tdt4240_project.services.api;

import com.example.eirik.tdt4240_project.models.User;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by Sindre on 4/22/2017.
 */

public interface UserApi {

    @GET("user/{username}")
    Observable<User> loginUser(@Path("username") String username, @Query("token") String token);

    @POST("user")
    Observable<User> createUser(@Query("username") String username, @Query("token") String token);

    @POST("user/registertoken")
    Observable<String> updateDeviceToken(@Query("username") String username, @Query("token") String token);
}


