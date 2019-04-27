package com.example.kamal.saatzanhamrah.webService;

import com.example.kamal.saatzanhamrah.webService.model.Retrofit_LoginModel_list;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("login_20190329.php")
    @FormUrlEncoded
    Call<Retrofit_LoginModel_list> login(@Field("user") String user, @Field("kind") String kind, @Field("pass") String pass, @Field("key_text_android") String key_text_android);
}
