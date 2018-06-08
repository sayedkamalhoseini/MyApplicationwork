package com.example.kamal.saatzanhamrah.ForgotPackage;

import android.app.Activity;

import com.android.volley.Request;
import com.example.kamal.saatzanhamrah.Share;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kamal on 1/21/2018.
 */

public class ForgotModel {
    Activity activity;
    ForgotPresenter presenter;
    public ForgotModel(ForgotActivity forgotActivity) {
        this.activity=forgotActivity;
    }

    public void setPresenter(ForgotPresenter forgotPresenter) {
        this.presenter=forgotPresenter;
    }

    public void sendEmailPrsenter(String url, final String email) {
        Share.getStringResponse(activity, Request.Method.POST, url, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                presenter.resultEmailPresenter(result);
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public Map onMapPost() {
                Map<String, String> serverParams = new HashMap<>();
                serverParams.put("email",email);
                serverParams.put("key_text_android", "ktaa");
                return serverParams;
            }
        });
    }
}
