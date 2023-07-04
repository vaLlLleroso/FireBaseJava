package com.example.firebasejava;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            Bundle b = ActivityOptions.makeSceneTransitionAnimation(Splash.this).toBundle();
            startActivity(new Intent(Splash.this, Login.class),b);
            finish();
        }, 3000);
    }
}