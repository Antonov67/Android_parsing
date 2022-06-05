package com.example.wiki_change;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static Retrofit retrofit;
    public static NetworkService Instance;


    public NetworkService(String path) {
        Log.d("wiki777","retrofit build start...");
        Log.d("wiki777",path);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder().baseUrl(path).addConverterFactory(GsonConverterFactory.create(gson)).build();
        Log.d("wiki777","retrofit ready");

    }

    public static NetworkService getInstance(String path) {

        if (Instance == null) {

            Log.d("wiki777", path);
            Instance = new NetworkService(path);
        }
        return Instance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
