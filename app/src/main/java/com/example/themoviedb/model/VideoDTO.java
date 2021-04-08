package com.example.themoviedb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoDTO {

    @SerializedName("id")
    private Integer id;
    @SerializedName("results")
    private List<ResultVideosDTO> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ResultVideosDTO> getResults() {
        return results;
    }

    public void setResults(List<ResultVideosDTO> results) {
        this.results = results;
    }
}
