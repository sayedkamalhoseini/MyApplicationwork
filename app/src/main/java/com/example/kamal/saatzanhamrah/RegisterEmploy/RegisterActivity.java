package com.example.kamal.saatzanhamrah.RegisterEmploy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kamal.saatzanhamrah.MySingleton;
import com.example.kamal.saatzanhamrah.Share;

public class RegisterActivity extends AppCompatActivity {
    RegisterView view;
    RegisterModel model;
    RegisterPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=new RegisterView(this);
        setContentView(view);
        model=new RegisterModel(this);
        presenter=new RegisterPresenter(model,view);
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
