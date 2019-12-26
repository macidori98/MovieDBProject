package com.example.moviedbproject.interfaces;

import com.example.moviedbproject.tmdb.model.NewToken;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {

    @GET("/3/authentication/token/new")
    Call<NewToken> getNewToken(@Query("api_key") String api_key);
}
