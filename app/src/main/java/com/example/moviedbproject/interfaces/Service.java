package com.example.moviedbproject.interfaces;

import com.example.moviedbproject.tmdb.model.ImageResponse;
import com.example.moviedbproject.tmdb.model.MoviesResponse;
import com.example.moviedbproject.tmdb.model.NewToken;
import com.example.moviedbproject.tmdb.model.VideoResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
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

    @GET("3/movie/{movie_id}/videos")
    Call<VideoResponse> getMovieVideo(@Path("movie_id") String movie_id, @Query("api_key") String api_key,
                                      @Query("language") String language);

    @GET("/3/movie/{movie_id}/images")
    Call<ImageResponse> getMovieImages(@Path("movie_id") String movie_id, @Query("api_key") String api_key);
}
