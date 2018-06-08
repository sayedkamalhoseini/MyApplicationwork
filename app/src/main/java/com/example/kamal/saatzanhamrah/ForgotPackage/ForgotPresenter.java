package com.example.kamal.saatzanhamrah.ForgotPackage;

/**
 * Created by kamal on 1/21/2018.
 */

public class ForgotPresenter {
    ForgotView view;
    ForgotModel model;

    public ForgotPresenter(ForgotModel model, ForgotView view) {
        this.view=view;
        this.model=model;
    }

    public void onCreate() {
        view.setPresenter(this);
        model.setPresenter(this);
    }

    public void sendEmailPresenter(String url, String email) {
        model.sendEmailPrsenter(url,email);
    }

    public void resultEmailPresenter(String result) {
        view.resultEmailView(result);
    }
}
