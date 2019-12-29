package com.example.moviedbproject.utils;

import com.example.moviedbproject.database.model.User;
import com.example.moviedbproject.tmdb.model.Movies;

public class Constant {
    public static final String BASE_URL = "https://api.themoviedb.org";
    public static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/";
    public static final String BASE_URL_VIDEO = "https://www.youtube.com/watch";
    public static final String API_KEY = "55ad3422681300f02641616898bdbee0";
    public static final String DEV_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NWFkMzQyMjY4MTMwMGYwMjY0MTYxNjg5OGJkYmVlMCIsInN1YiI6IjVlMDQ2NzgzNGMxZDlhMDAxNWRiZmE1MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7N8vGrQ9oxUyaQljW2WfrUoDxJ2Hjeb63fRTCsnDN4I";
    public static final int GET_SUCCESS_CODE = 200;
    public static final int POST_SUCCESS_CODE = 201;
    public static final String DETAIL_DIALOG_TAG = "Detail Dialog";
    public static final String CHANGE_PASSWORD_DIALOG_TAG = "Detail Dialog";
    public static final String ORIGINAL_COVER_DIALOG_TAG = "Original Cover Dialog";
    public static final String CANCEL_DESCRIPTION = "CANCEL DESCRIPTION";
    public static final String CANCEL_ORIGINAL_COVER = "CANCEL ORIGINAL COVER";
    public static final String GET_DATA = "Getting data";
    public static final String INSERT_DATA = "Inserting data";
    public static final String DESCRIPTION = "Description\n\n";
    public static final String ORIGINAL_COVER = "Original cover";
    public static final String FAIL = "FAIL";
    public static final String SUCCESSFUL = "INSERT SUCCESSFUL";
    public static final String CANCEL_FAIL = "CANCEL FAIL";
    public static User CURRENT_USER;
    public static String CURRENT_TOKEN;
    public static long PAGES_NUMBER;
    public static Movies SELECTED_MOVIE;
}
