package com.example.kamal.saatzanhamrah.ForgotPackage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kamal.saatzanhamrah.MySingleton;
import com.example.kamal.saatzanhamrah.Share;

public class ForgotActivity extends AppCompatActivity {

    ForgotView view;
    ForgotModel model;
    ForgotPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view=new ForgotView(this);
        setContentView(view);
        model=new ForgotModel(this);
        presenter=new ForgotPresenter(model,view);
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
