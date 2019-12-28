package com.example.moviedbproject.tmdb.model;

import java.util.ArrayList;
import java.util.List;

public class MoviesResponse {

    private long page;
    private long total_results;
    private long total_pages;
    private List<Movies> results = new ArrayList<>();

    public MoviesResponse(){}

    public MoviesResponse(long page, long total_results, long total_pages, List<Movies> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getTotal_results() {
        return total_results;
    }

    public void setTotal_results(long total_results) {
        this.total_results = total_results;
    }

    public long getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(long total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movies> getResults() {
        return results;
    }

    public void setResults(List<Movies> results) {
        this.results = results;
    }
}
