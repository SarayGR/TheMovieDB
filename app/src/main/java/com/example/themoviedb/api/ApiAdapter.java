package com.example.themoviedb.api;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapter {

    private static ApiService API_SERVICE;

    public static ApiService getApiService() {
        String baseUrl = "https://api.themoviedb.org/";

        if(API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API_SERVICE = retrofit.create(ApiService.class);
        }

        return API_SERVICE;

    }

}
