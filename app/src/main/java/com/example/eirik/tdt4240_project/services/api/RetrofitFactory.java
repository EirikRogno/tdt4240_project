package com.example.eirik.tdt4240_project.services.api;

import com.example.eirik.tdt4240_project.AppController;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sindre on 4/22/2017.
 */

public class RetrofitFactory {

    public static Retrofit getRetrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(AppController.getInstance().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }
}
