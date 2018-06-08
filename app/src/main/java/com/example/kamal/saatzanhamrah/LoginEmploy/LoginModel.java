package com.example.kamal.saatzanhamrah.LoginEmploy;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.kamal.saatzanhamrah.ForgotPackage.ForgotActivity;
import com.example.kamal.saatzanhamrah.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.RegisterEmploy.RegisterActivity;
import com.example.kamal.saatzanhamrah.Share;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kamal on 12/9/2017.
 */

public class LoginModel {
    LoginActivity activity;
    LoginPresenter presenter;

    public LoginModel(LoginActivity loginActivity) {

        this.activity = loginActivity;
    }

    public void setPresenter(LoginPresenter loginPresenter) {
        this.presenter = loginPresenter;
    }

    public void registerModel() {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

    public void loginModel(final String user, final String pass, final String kind, String url, final ProgressBar progressBar, final Button btnLogin) {
        if (user.equals("") || pass.equals("")) {
            Toast.makeText(activity, "لطفا نام کاربری و رمز عبور را وارد کنید.", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            btnLogin.setEnabled(true);
            return;
        }
        Share.getStringResponse(activity, Request.Method.POST, url, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                if (result.equals("find")) {
                   Share.saveSharePref(activity, "userKey", user);
                   Share.saveSharePref(activity, "passKey", pass);
                   Share.saveSharePref(activity, "kindKey", kind);
                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.putExtra("user",user);
                    intent.putExtra("kind",kind);
                    activity.startActivity(intent);
                    activity.finish();
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);
                } else if (result.equals("nofind")) {
                    Toast.makeText(activity, "لطفا ثبت نام نمایید و نوع شغل هم درست انتخاب کرده باشید.", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);

                } else if (result.equals("noconnect")) {
                    Toast.makeText(activity, activity.getString(R.string.registerError), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);

                } else {

                    Toast.makeText(activity, "خطا", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);


                }

            }

            @Override
            public void onError(String error) {
                Toast.makeText(activity, activity.getString(R.string.registerError), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                btnLogin.setEnabled(true);
            }


            @Override
            public Map onMapPost() {
                Map<String, String> loginparams = new HashMap<>();
                loginparams.put("email", user);
                loginparams.put("pass", pass);
                loginparams.put("kind", kind);
                loginparams.put("key_text_android", "ktaa");
                return loginparams;
            }
        });

    }

    public void forgotModel() {
        Intent intent = new Intent(activity, ForgotActivity.class);
        activity.startActivity(intent);

    }
}
