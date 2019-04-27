package com.example.kamal.saatzanhamrah.LoginEmploy;

import android.widget.Button;
import android.widget.ProgressBar;

/**
 * Created by kamal on 12/9/2017.
 */

public class LoginPresenter {
    LoginModel model;
    LoginView view;
    public LoginPresenter(LoginModel model, LoginView view) {
        this.model=model;
        this.view=view;
    }

    public void onCreate() {
        view.setPresenter(this);
        model.setPresenter(this);
    }

    public void registerPresenter() {
        model.registerModel();
    }

    public void loginPresenter(String user, String pass, String kind, ProgressBar progressBar, Button btnLogin) {
        model.loginModel(user,pass,kind,progressBar,btnLogin);
    }

    public void forgotPresenter() {
        model.forgotModel();
    }
}
