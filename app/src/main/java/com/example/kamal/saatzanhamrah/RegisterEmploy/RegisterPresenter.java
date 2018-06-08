package com.example.kamal.saatzanhamrah.RegisterEmploy;

import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Map;

/**
 * Created by kamal on 12/6/2017.
 */

public class RegisterPresenter {
    RegisterView view;
    RegisterModel model;
    public RegisterPresenter(RegisterModel model, RegisterView view) {
        this.view=view;
        this.model=model;
    }


    public void onCreate() {
        view.setPresenter(this);
        model.setPresenter(this);
    }


    public void passParams(Map<String, String> params, String url, ProgressBar progressBar, Button registerButton) {
        model.employPostParams(params,url,progressBar,registerButton);
    }

    public void resultPresenter(String result) {
        view.resultView(result);
    }
}
