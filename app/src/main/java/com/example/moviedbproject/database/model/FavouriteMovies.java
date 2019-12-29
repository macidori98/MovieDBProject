package com.example.moviedbproject.database.model;

import java.util.List;

public class FavouriteMovies {
    public static final String TABLE_NAME = "favourite_movies";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_POPULARITY = "popularity";
    public static final String COLUMN_VOTE_COUNT = "vote_count";
    public static final String COLUMN_VIDEO = "video";
    public static final String COLUMN_POSTER_PATH = "poster_path";
    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_ADULT = "adult";
    public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
    public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
    public static final String COLUMN_ORIGINAL_TITLE = "original_title";
    public static final String COLUMN_GENRE_IDS = "genre_ids";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_VOTE_AVERAGE = "vote_average";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_USER_ID = "user_id";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_POPULARITY + " FLOAT,"
                    + COLUMN_VOTE_COUNT + " INTEGER,"
                    + COLUMN_VIDEO + " BOOLEAN,"
                    + COLUMN_POSTER_PATH + " TEXT,"
                    + COLUMN_MOVIE_ID + " INTEGER,"
                    + COLUMN_ADULT + " BOOLEAN,"
                    + COLUMN_BACKDROP_PATH + " TEXT,"
                    + COLUMN_ORIGINAL_LANGUAGE + " TEXT,"
                    + COLUMN_ORIGINAL_TITLE + " TEXT,"
                    + COLUMN_GENRE_IDS + " ARRAY,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_VOTE_AVERAGE + " FLOAT,"
                    + COLUMN_OVERVIEW + " TEXT,"
                    + COLUMN_RELEASE_DATE + " TEXT,"
                    + COLUMN_USER_ID + " INTEGER"
                    + ")";

    private int id;
    private double popularity;
    private long vote_count;
    private boolean video;
    private String poster_path;
    private long movie_id;
    private boolean adult;
    private String backdrop_path;
    private String original_language;
    private String original_title;
    private List<Integer> genre_ids;
    private String title;
    private float vote_average;
    private String overview;
    private String release_date;
    private int user_id;

    public FavouriteMovies(int id, double popularity, long vote_count, boolean video, String poster_path, long movie_id, boolean adult, String backdrop_path, String original_language, String original_title, List<Integer> genre_ids, String title, float vote_average, String overview, String release_date, int user_id) {
        this.id = id;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.video = video;
        this.poster_path = poster_path;
        this.movie_id = movie_id;
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.genre_ids = genre_ids;
        this.title = title;
        this.vote_average = vote_average;
        this.overview = overview;
        this.release_date = release_date;
        this.user_id = user_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public long getVote_count() {
        return vote_count;
    }

    public void setVote_count(long vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(long movie_id) {
        this.movie_id = movie_id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
