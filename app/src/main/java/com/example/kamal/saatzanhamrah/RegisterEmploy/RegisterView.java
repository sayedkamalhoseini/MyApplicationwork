package com.example.kamal.saatzanhamrah.RegisterEmploy;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.kamal.saatzanhamrah.LoginEmploy.LoginActivity;
import com.example.kamal.saatzanhamrah.MainPackage.MainActivity;
import com.example.kamal.saatzanhamrah.R;
import com.example.kamal.saatzanhamrah.Share;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kamal on 12/6/2017.
 */

public class RegisterView extends FrameLayout implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    RegisterPresenter presenter;
    Spinner spinnerEmploy;
    EditText editusername, editpassword, editEmail;
    Button registerButton, loginPage;
    Map<String, String> params;
    Activity activity;
    String employeeUrl = "http://kamalroid.ir/register.php";
    private String selectKind, kind = "";
    private Toolbar toolbar;
    private ProgressBar progressBar;

    public RegisterView(@NonNull AppCompatActivity activity) {
        super(activity);
        this.activity = activity;
        View view = inflate(activity, R.layout.activity_register, this);
        editusername = (EditText) view.findViewById(R.id.userNameEditText);
        editpassword = (EditText) view.findViewById(R.id.passwordEditText);
        editEmail = (EditText) view.findViewById(R.id.emailEditText);
        spinnerEmploy = (Spinner) view.findViewById(R.id.spinner_register_employ);
        registerButton = (Button) view.findViewById(R.id.registerButton);
        loginPage = (Button) view.findViewById(R.id.button_login_page);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_register_loading);
        editusername.requestFocus();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary));
        Share.spinnerAdapter(activity, spinnerEmploy, R.array.arrayemploy);
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
        spinnerEmploy.setOnItemSelectedListener(this);
        registerButton.setOnClickListener(this);
        loginPage.setOnClickListener(this);
    }

    private void createParams() {
        params = new HashMap<>();
        params.put("username", editusername.getText().toString().trim());
        params.put("password", editpassword.getText().toString().trim());
        params.put("email", editEmail.getText().toString().trim());
    }

    public void setPresenter(RegisterPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.registerButton:

                switch (kind) {
                    case "":
                        Toast.makeText(activity, "لطفا نوع شغل تان را انتخاب کنید.", Toast.LENGTH_LONG).show();
                        break;
                    case "employer":
                        if (Share.check(getContext())) {
                            registerButton.setEnabled(false);
                            progressBar.setVisibility(View.VISIBLE);
                            createParams();
                            params.put("kind", kind);
                            presenter.passParams(params, employeeUrl, progressBar, registerButton);
                            break;
                        } else {
                            Toast.makeText(activity, getResources().getString(R.string.noInternet), Toast.LENGTH_LONG).show();
                            break;
                        }
                    case "employee":
                        if (Share.check(getContext())) {
                            registerButton.setEnabled(false);
                            progressBar.setVisibility(View.VISIBLE);
                            createParams();
                            params.put("kind", kind);
                            presenter.passParams(params, employeeUrl, progressBar, registerButton);
                            break;
                        } else {
                            Toast.makeText(activity, getResources().getString(R.string.noInternet), Toast.LENGTH_LONG).show();
                            break;
                        }
                }
                break;
            case R.id.button_login_page:
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);
                activity.finish();
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectKind = parent.getItemAtPosition(position).toString();
        switch (selectKind) {
            case "کارفرما":
                kind = "employer";
                break;
            case "کارگر/ کارمند":
                kind = "employee";
                break;
            case "نوع شغل تان را انتخاب کنید.":
                kind = "";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(activity, "Selected Item is nothing", Toast.LENGTH_SHORT).show();

    }

    public void resultView(String result) {
        switch (result) {
            case "done":
                registerButton.setEnabled(true);
                Toast.makeText(activity, activity.getResources().getString(R.string.registerSuccessfully), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);
                break;
            case "fillfield":
                registerButton.setEnabled(true);
                Toast.makeText(activity, activity.getResources().getString(R.string.fillField), Toast.LENGTH_LONG).show();
                break;
            case "this user there is":
                registerButton.setEnabled(true);
                Toast.makeText(activity, activity.getResources().getString(R.string.repeatUser), Toast.LENGTH_LONG).show();
                break;
            case "failer_interesting_database":
                registerButton.setEnabled(true);
                Toast.makeText(activity, activity.getResources().getString(R.string.registerNull), Toast.LENGTH_LONG).show();
                break;
            case "failure_post":
                registerButton.setEnabled(true);
                Toast.makeText(activity, activity.getResources().getString(R.string.registerError), Toast.LENGTH_LONG).show();
                break;
            default:
                registerButton.setEnabled(true);
                Toast.makeText(activity, activity.getResources().getString(R.string.registerError), Toast.LENGTH_LONG).show();
                break;
        }

    }
}
