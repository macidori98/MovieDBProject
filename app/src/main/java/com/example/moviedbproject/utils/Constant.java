package com.example.moviedbproject.utils;

import com.example.moviedbproject.database.model.User;

public class Constant {
    public static final String BASE_URL = "https://api.themoviedb.org";
    public static final String API_KEY = "55ad3422681300f02641616898bdbee0";
    public static final String DEV_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NWFkMzQyMjY4MTMwMGYwMjY0MTYxNjg5OGJkYmVlMCIsInN1YiI6IjVlMDQ2NzgzNGMxZDlhMDAxNWRiZmE1MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.7N8vGrQ9oxUyaQljW2WfrUoDxJ2Hjeb63fRTCsnDN4I";
    public static User CURRENT_USER;
    public static String CURRENT_TOKEN;
    public static final int GET_SUCCESS_CODE = 200;
    public static final int POST_SUCCESS_CODE = 201;

}
