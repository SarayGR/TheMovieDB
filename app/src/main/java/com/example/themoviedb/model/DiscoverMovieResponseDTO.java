package com.example.themoviedb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DiscoverMovieResponseDTO {

    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<DiscoverMovieDTO> results;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("total_results")
    private Integer totalResults;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<DiscoverMovieDTO> getResults() {
        return results;
    }

    public void setResults(List<DiscoverMovieDTO> results) {
        this.results = results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }
}
