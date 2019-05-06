package com.example.kamal.saatzanhamrah.LoginEmploy;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kamal.saatzanhamrah.MainPackage.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;

import java.util.logging.Handler;

/**
 * Created by kamal on 12/9/2017.
 */

public class LoginView extends FrameLayout implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    LoginPresenter presenter;
    Activity activity;
    EditText txtEdituser;
    EditText txtEditpassword;
    Button btnLogin, btnRegister, btnForgot;
    CheckBox checkBox;
    Spinner spinner;
    private ProgressBar progressBar;
    String url = "http://www.kamalroid.ir/login_20190329.php";
    int selectionSpinner = 0;
    String kind="";
    private Toolbar toolbar;


    public LoginView(@NonNull AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
        View view = inflate(activity, R.layout.activity_login, this);
        txtEdituser = (EditText) view.findViewById(R.id.editText_login_userName);
        txtEditpassword = (EditText) view.findViewById(R.id.editText_login_password);
        btnLogin = (Button) view.findViewById(R.id.button_login_signIn);
        btnRegister = (Button) view.findViewById(R.id.button_login_signUp);
        btnForgot = (Button) view.findViewById(R.id.button_login_forgot);
        checkBox = (CheckBox) view.findViewById(R.id.checkBox_login_save);
        spinner = (Spinner) view.findViewById(R.id.spinner_login_employ);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_login_loading);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));

        if (Share.loadPref(activity, "userKey") != "" && Share.loadPref(activity, "passKey") != "" && Share.loadPref(activity, "kindKey") != "") {
            if(Share.loadPref(activity,"userKeyUpdate")==""){
                Share.saveSharePref(activity,"userKeyUpdate",Share.loadPref(activity, "userKey"));
            }
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("user", Share.loadPref(activity, "userKey"));
            intent.putExtra("kind", Share.loadPref(activity, "kindKey"));
            activity.startActivity(intent);
            activity.finish();
        }
        Share.spinnerAdapter(activity, spinner, R.array.arrayemploy);
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnForgot.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
    }

    public void setPresenter(LoginPresenter loginPresenter) {
        this.presenter = loginPresenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login_signUp:
                presenter.registerPresenter();
                break;
            case R.id.button_login_signIn:
                if (Share.check(getContext())) {
                    if(kind.equals("")){
                        Toast.makeText(activity, "لطفا نوع شغل تان را انتخاب کنید.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    btnLogin.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                    presenter.loginPresenter(txtEdituser.getText().toString().trim(), txtEditpassword.getText().toString().trim(), kind, url, progressBar, btnLogin);
                    break;
                } else {
                    Toast.makeText(activity, getResources().getString(R.string.noInternet), Toast.LENGTH_LONG).show();
                    break;
                }
            case R.id.button_login_forgot:
                presenter.forgotPresenter();
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                break;
            case 1:
                selectionSpinner = 1;
                kind = "employer";
                break;
            case 2:
                selectionSpinner = 2;
                kind = "employee";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
