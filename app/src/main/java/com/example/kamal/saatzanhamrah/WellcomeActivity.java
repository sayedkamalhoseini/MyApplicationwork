package com.example.kamal.saatzanhamrah;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.kamal.saatzanhamrah.MainPackage.MainActivity;
import com.example.kamal.saatzanhamrah.RegisterEmploy.RegisterActivity;

public class WellcomeActivity extends AppCompatActivity {

    ImageView imageView;
    private Animation  animation1,animation2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        imageView = (ImageView) findViewById(R.id.circleImageView);
//        logo.setVisibility(View.VISIBLE);
        animation1 = AnimationUtils.loadAnimation(WellcomeActivity.this,
                R.anim.fade_in);
        animation1.setDuration(2000);

        imageView.startAnimation(animation1);

        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                text.setVisibility(View.VISIBLE);
                navigateToNext();

//                animation2 = AnimationUtils.loadAnimation(SplashActivity.this,
//                        R.anim.exit_to_right);
//               animation2.setDuration(2000);
////                text.startAnimation(animation2);
//
//                animation2.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    public void navigateToNext() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Share.loadPref(WellcomeActivity.this, "userKey") != "" && Share.loadPref(WellcomeActivity.this, "passKey") != "" && Share.loadPref(WellcomeActivity.this, "kindKey") != "") {
                    if (Share.loadPref(WellcomeActivity.this, "userKeyUpdate") == "") {
                        Share.saveSharePref(WellcomeActivity.this, "userKeyUpdate", Share.loadPref(WellcomeActivity.this, "userKey"));
                    }
                    Intent intent = new Intent(WellcomeActivity.this, MainActivity.class);
                    intent.putExtra("user", Share.loadPref(WellcomeActivity.this, "userKey"));
                    intent.putExtra("kind", Share.loadPref(WellcomeActivity.this, "kindKey"));
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(WellcomeActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 500);

    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}
