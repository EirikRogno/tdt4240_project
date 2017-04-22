package com.example.eirik.tdt4240_project.services.api;

import com.example.eirik.tdt4240_project.models.User;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * Created by Sindre on 4/22/2017.
 */

public interface UserApi {

    @POST("users")
    Call<User> createUser(@Body JSONObject user);
}
