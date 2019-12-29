package com.example.moviedbproject.tmdb.model;

import java.util.ArrayList;
import java.util.List;

public class ImageResponse {
    private long id;
    private List<Image> backdrops = new ArrayList<>();
    private List<Image> posters = new ArrayList<>();

    public ImageResponse(long id, List<Image> imageList, List<Image> posters) {
        this.id = id;
        this.backdrops = imageList;
        this.posters = posters;
    }

    public List<Image> getPosters() {
        return posters;
    }

    public void setPosters(List<Image> posters) {
        this.posters = posters;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Image> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Image> backdrops) {
        this.backdrops = backdrops;
    }
}
