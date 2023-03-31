package com.example.bookhotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookhotel.LoginActivity;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private TextView name,slogantxt;
    private ImageView logo;
    private View topview1, topview2,topview3;
    private View bottomview1,bottommview2,bottomview3;
    private int count = 0;
    private int TIME_OUT = 4500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//hide navigation bar to make activity appear full screen
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
        setContentView(R.layout.activity_splash_screen);

        name = findViewById(R.id.name);
        slogantxt = findViewById(R.id.slogantxt);

        logo = findViewById(R.id.logo);

        topview1 = findViewById(R.id.topview1);
        topview2 = findViewById(R.id.topview2);
        topview3 = findViewById(R.id.topview3);

        bottomview1 = findViewById(R.id.bottomview1);
        bottommview2 = findViewById(R.id.bottomview2);
        bottomview3 = findViewById(R.id.bottomview3);

        Animation logoAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.zoom_animation);
        Animation nameAnimation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.zoom_animation);

        Animation topView1Animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.top_views_animations);
        Animation topView2Animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.top_views_animations);
        Animation topView3Animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.top_views_animations);

        Animation bottomView1Animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bottom_views_animations);
        Animation bottomView2Animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bottom_views_animations);
        Animation bottomView3Animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bottom_views_animations);

        topview1.startAnimation(topView1Animation);
        bottomview1.startAnimation(bottomView1Animation);

        topView1Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                topview2.setVisibility(View.VISIBLE);
                bottommview2.setVisibility(View.VISIBLE);

                topview2.startAnimation(topView2Animation);
                bottommview2.startAnimation(bottomView2Animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        topView2Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                topview3.setVisibility(View.VISIBLE);
                bottomview3.setVisibility(View.VISIBLE);

                topview3.startAnimation(topView3Animation);
                bottomview3.startAnimation(bottomView3Animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        topView3Animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                logo.setVisibility(View.VISIBLE);
                logo.startAnimation(logoAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        logoAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                name.setVisibility(View.VISIBLE);
                name.startAnimation(nameAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        nameAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                slogantxt.setVisibility(View.VISIBLE);
                final String animatetxt = slogantxt.getText().toString();
                slogantxt.setText("");
                count = 0;
                new CountDownTimer(animatetxt.length() * 100, 100){

                    @Override
                    public void onTick(long millisUntilFinished) {

                        slogantxt.setText(slogantxt.getText().toString()+animatetxt.charAt(count));
                        count++;
                    }

                    @Override
                    public void onFinish() {
//                        startActivity(new Intent(this,LoginActivity.class));
                        startActivityTwo();

                    }

                }.start();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

//        startActivity(new Intent(this,LoginActivity.class));
//        new Handler().postDelayed(
//                {
//                        startActivity(new Intent(this,LoginActivity.class));
//        finish();
//                },TIME_OUT.toLong());




    }

    private void startActivityTwo() {
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}