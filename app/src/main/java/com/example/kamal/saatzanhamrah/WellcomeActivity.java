package com.example.kamal.saatzanhamrah;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kamal.saatzanhamrah.RegisterEmploy.RegisterActivity;

public class WellcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(WellcomeActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        },500);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
