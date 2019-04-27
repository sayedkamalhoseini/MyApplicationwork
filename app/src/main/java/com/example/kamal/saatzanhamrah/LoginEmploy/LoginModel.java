package com.example.kamal.saatzanhamrah.LoginEmploy;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.kamal.saatzanhamrah.ForgotPackage.ForgotActivity;
import com.example.kamal.saatzanhamrah.MainPackage.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.RegisterEmploy.RegisterActivity;
import com.example.kamal.saatzanhamrah.Share;
import com.example.kamal.saatzanhamrah.VisitEmployeeToEmployer.VisitEmployee;
import com.example.kamal.saatzanhamrah.webService.APIClient;
import com.example.kamal.saatzanhamrah.webService.APIInterface;
import com.example.kamal.saatzanhamrah.webService.model.Retrofit_LoginModel;
import com.example.kamal.saatzanhamrah.webService.model.Retrofit_LoginModel_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kamal on 12/9/2017.
 */

public class LoginModel {
    LoginActivity activity;
    LoginPresenter presenter;
    String userNameMain, success;

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

    public void forgotModel() {
        Intent intent = new Intent(activity, ForgotActivity.class);
        activity.startActivity(intent);

    }

    public void loginModel(final String user, final String pass, final String kind, final ProgressBar progressBar, final Button btnLogin) {
        if (user.equals("") || pass.equals("")) {
            Toast.makeText(activity, "لطفا نام کاربری و رمز عبور را وارد کنید.", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
            btnLogin.setEnabled(true);
            return;
        }
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Retrofit_LoginModel_list> call = apiInterface.login(user, kind, pass, "ktaa");

        call.enqueue(new Callback<Retrofit_LoginModel_list>() {
            @Override
            public void onResponse(Call<Retrofit_LoginModel_list> call, Response<Retrofit_LoginModel_list> response) {
                if (response.isSuccessful()) {
                   List<Retrofit_LoginModel> list=response.body().getResult1();

                   for (Retrofit_LoginModel retrofit_loginModel : list){
                       success=retrofit_loginModel.getSuccess();
                       userNameMain=retrofit_loginModel.getUsername();
                   }

                    if (success.equals("find")) {
                        Share.saveSharePref(activity, "userKey", userNameMain);
                        Share.saveSharePref(activity, "userKeyUpdate", user);
                        Share.saveSharePref(activity, "passKey", pass);
                        Share.saveSharePref(activity, "kindKey", kind);
                        Intent intent = new Intent(activity, MainActivity.class);
                        intent.putExtra("user", userNameMain);
                        intent.putExtra("kind", kind);
                        activity.startActivity(intent);
                        activity.finish();
                        progressBar.setVisibility(View.GONE);
                        btnLogin.setEnabled(true);
                    } else if (success.equals("noFind")) {
                        Toast.makeText(activity, "لطفا ثبت نام نمایید و نوع شغل هم درست انتخاب کرده باشید.", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        btnLogin.setEnabled(true);
                    } else if (success.equals("noConnect")) {
                        Toast.makeText(activity, activity.getString(R.string.registerError), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        btnLogin.setEnabled(true);

                    } else {
                        Toast.makeText(activity, "خطا", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        btnLogin.setEnabled(true);
                    }


                }
            }

            @Override
            public void onFailure(Call<Retrofit_LoginModel_list> call, Throwable t) {
                if(t instanceof IOException){
                    Toast.makeText(activity, activity.getString(R.string.registerError), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);
                }

            }
        });

    }
}
