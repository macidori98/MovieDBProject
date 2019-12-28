package com.example.moviedbproject.interfaces;

import com.example.moviedbproject.tmdb.model.Movies;
import com.example.moviedbproject.tmdb.model.MoviesResponse;
import com.example.moviedbproject.tmdb.model.NewToken;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @GET("/3/authentication/token/new")
    Call<NewToken> getNewToken(@Query("api_key") String api_key);

    @GET("/3/discover/movie?api_key=55ad3422681300f02641616898bdbee0&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
    Call<MoviesResponse> getTopMovies();
}
