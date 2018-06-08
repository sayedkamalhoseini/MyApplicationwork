package com.example.kamal.saatzanhamrah.LoginEmploy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.example.kamal.saatzanhamrah.MySingleton;
import com.example.kamal.saatzanhamrah.Share;


public class LoginActivity extends AppCompatActivity {
    LoginView view;
    LoginPresenter presenter;
    LoginModel model;
    RequestQueue myrequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=new LoginView(this);
        setContentView(view);
        model=new LoginModel(this);
        presenter=new LoginPresenter(model,view);
        presenter.onCreate();
     }


    @Override
    protected void onStop() {
        super.onStop();
        if ( MySingleton.getInstance(this).getRequestQueue()!= null) {
            MySingleton.getInstance(this).getRequestQueue().cancelAll(Share.TAG);
        }
    }
}

