package com.example.themoviedb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GenreListDTO implements Serializable {
    @SerializedName("genres")
    private List<GenreDTO> genres;

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }
}
