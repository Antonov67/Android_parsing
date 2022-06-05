package com.example.wiki_change;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("/w/index.php")
    Call<String> getDate(@Query("title") String title, @Query("action") String action);
}
