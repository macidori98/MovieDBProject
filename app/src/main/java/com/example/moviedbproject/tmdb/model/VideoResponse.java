package com.example.moviedbproject.tmdb.model;

import java.util.ArrayList;
import java.util.List;

public class VideoResponse {
    private long id;
    private List<Video> videoList = new ArrayList<>();

    public VideoResponse(long id, List<Video> videoList) {
        this.id = id;
        this.videoList = videoList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}
