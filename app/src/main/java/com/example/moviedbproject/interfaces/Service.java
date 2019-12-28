package com.example.moviedbproject.interfaces;

import com.example.moviedbproject.tmdb.model.MoviesResponse;
import com.example.moviedbproject.tmdb.model.NewToken;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("/3/authentication/token/new")
    Call<NewToken> getNewToken(@Query("api_key") String api_key);

    @GET("/3/discover/movie")
    Call<MoviesResponse> getTopMovies(@Query("api_key") String api_key, @Query("language") String language,
                                      @Query("sort_by") String sort_by, @Query("include_adult") String include_adult,
                                      @Query("include_video") String include_video, @Query("page") String page);

    @GET("/3/search/movie")
    Call<MoviesResponse> getSearchedMovies(@Query("api_key") String api_key, @Query("language") String language,
                                           @Query("query") String query, @Query("page") String page,
                                           @Query("include_adult") String include_adult);
}
