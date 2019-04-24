package com.example.kamal.saatzanhamrah.webService;

import com.example.kamal.saatzanhamrah.webService.model.Retrofit_LoginModel_list;

import retrofit2.Call;
import retrofit2.http.POST;

public interface APIInterface {
    
    @POST
    Call<Retrofit_LoginModel_list> login();
}
