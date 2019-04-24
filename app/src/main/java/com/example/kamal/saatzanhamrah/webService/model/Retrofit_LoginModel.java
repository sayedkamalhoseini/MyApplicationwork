package com.example.kamal.saatzanhamrah.webService.model;

import com.google.gson.annotations.SerializedName;

public class Retrofit_LoginModel {
    @SerializedName("success")
    private String success;
    @SerializedName("username")
    private String username;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
