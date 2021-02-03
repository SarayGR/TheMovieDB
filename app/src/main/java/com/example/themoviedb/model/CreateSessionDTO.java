package com.example.themoviedb.model;

import com.google.gson.annotations.SerializedName;

public class CreateSessionDTO {

    @SerializedName("request_token")
    private String requestToken;

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

}
