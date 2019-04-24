package com.example.kamal.saatzanhamrah.webService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static final String BASE_URL="http://www.kamalroid.ir/";
    private static Retrofit retrofit=null;

    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
