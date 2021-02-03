package com.example.themoviedb.model;

import com.google.gson.annotations.SerializedName;

public class LoginSessionDTO {

    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("request_token")
    private String requestToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }

}
