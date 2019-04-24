package com.example.kamal.saatzanhamrah.webService.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Retrofit_LoginModel_list {
    public List<Retrofit_LoginModel> getResult1() {
        return result1;
    }

    public void setResult1(List<Retrofit_LoginModel> result1) {
        this.result1 = result1;
    }

    @SerializedName("result1")
    List<Retrofit_LoginModel> result1;
}
