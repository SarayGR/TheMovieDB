package com.example.themoviedb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConfigurationApiDTO {

    @SerializedName("images")
    private ImageDTO images;
    @SerializedName("change_keys")
    private List<String> changeKeys = null;

    public ImageDTO getImages() {
        return images;
    }

    public void setImages(ImageDTO images) {
        this.images = images;
    }

    public List<String> getChangeKeys() {
        return changeKeys;
    }

    public void setChangeKeys(List<String> changeKeys) {
        this.changeKeys = changeKeys;
    }
}
