package com.example.themoviedb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DiscoverTVResponseDTO {

    @SerializedName("page")
    private Integer page;
    @SerializedName("results")
    private List<DiscoverTVProgramsDTO> tvProgramsDTOList = null;
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

    public List<DiscoverTVProgramsDTO> getTvProgramsDTOList() {
        return tvProgramsDTOList;
    }

    public void setTvProgramsDTOList(List<DiscoverTVProgramsDTO> tvProgramsDTOList) {
        this.tvProgramsDTOList = tvProgramsDTOList;
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
