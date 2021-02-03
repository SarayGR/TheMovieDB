package com.example.themoviedb.model;

import com.google.gson.annotations.SerializedName;

public class CreateSessionResponseDTO {

    @SerializedName("success")
    private Boolean success;
    @SerializedName("session_id")
    private String sessionId;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
