package com.example.kamal.saatzanhamrah.RegisterEmploy;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.kamal.saatzanhamrah.LoginEmploy.LoginActivity;
import com.example.kamal.saatzanhamrah.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.RoomPackage.AppDatabase;
import com.example.kamal.saatzanhamrah.RoomPackage.DatabaseInitializer;
import com.example.kamal.saatzanhamrah.RoomPackage.Employee;
import com.example.kamal.saatzanhamrah.Share;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kamal on 12/6/2017.
 */

public class RegisterModel {
    RegisterActivity activity;
    RegisterPresenter presenter;


    public RegisterModel(RegisterActivity registerActivity) {
        activity = registerActivity;
    }

    public void employPostParams(final Map<String, String> params, String url, final ProgressBar progressBar, final Button registerButton) {
        final String username = params.get("username");
        final String password = params.get("password");
        final String kind = params.get("kind");
        final String email = params.get("email");
        if (username.equals("") || password.equals("") || kind.equals("")) {
            Toast.makeText(activity, "لطفا اطلاعات را تکمیل فرمایید.", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }
        Share.getStringResponse(activity, Request.Method.POST, url, null, new Share.StringVolleyCallBack() {
            @Override
            public void onSuccessResponse(String result) {
                switch (result) {
                    case "done":
                        Toast.makeText(activity, activity.getResources().getString(R.string.registerSuccessfully), Toast.LENGTH_LONG).show();
                        Share.saveSharePref(activity, "userKey", username);
                        Share.saveSharePref(activity, "userKeyUpdate", username);
                        Share.saveSharePref(activity, "passKey", password);
                        Share.saveSharePref(activity, "kindKey", kind);
                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.putExtra("user", username);
                        intent.putExtra("kind", kind);
                        activity.startActivity(intent);
                        activity.finish();
                        DatabaseInitializer.populateAsync(AppDatabase.getAppDatabase(activity),username,password,username,email);
                        progressBar.setVisibility(View.GONE);
                        activity.startActivity(intent);
                        activity.finish();
                        break;
                    case "fillfield":
                        Toast.makeText(activity, activity.getResources().getString(R.string.fillField), Toast.LENGTH_LONG).show();
                        registerButton.setEnabled(true);
                        break;
                    case "this user there is":
                        Toast.makeText(activity, activity.getResources().getString(R.string.repeatUser), Toast.LENGTH_LONG).show();
                        registerButton.setEnabled(true);
                        break;
                    case "failer_interesting_database":
                        Toast.makeText(activity, activity.getResources().getString(R.string.registerNull), Toast.LENGTH_LONG).show();
                        registerButton.setEnabled(true);
                        break;
                    case "failure_post":
                        Toast.makeText(activity, activity.getResources().getString(R.string.registerError), Toast.LENGTH_LONG).show();
                        registerButton.setEnabled(true);
                        break;
                    default:
                        Toast.makeText(activity, activity.getResources().getString(R.string.registerError), Toast.LENGTH_LONG).show();
                        registerButton.setEnabled(true);
                        break;
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError(String error) {
                Toast.makeText(activity, activity.getString(R.string.registerError), Toast.LENGTH_SHORT).show();
                registerButton.setEnabled(true);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public Map onMapPost() {
                Map<String, String> serverParams = new HashMap<>();
                serverParams.put("user", username);
                serverParams.put("pass", password);
                serverParams.put("kind", kind);
                serverParams.put("email", email);
                serverParams.put("key_text_android", "ktaa");
                return serverParams;
            }
        });
    }

    public void setPresenter(RegisterPresenter registerPresenter) {
        this.presenter = registerPresenter;
    }


}
