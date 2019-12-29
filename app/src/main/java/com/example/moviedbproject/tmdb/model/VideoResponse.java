package com.example.moviedbproject.tmdb.model;

import java.util.ArrayList;
import java.util.List;

public class VideoResponse {
    private long id;
    private List<Video> results = new ArrayList<>();

    public VideoResponse(long id, List<Video> results) {
        this.id = id;
        this.results = results;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> video) {
        this.results = video;
    }
}
