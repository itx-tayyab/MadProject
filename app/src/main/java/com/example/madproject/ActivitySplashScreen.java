package com.example.madproject;

import android.os.Bundle;
import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;



public class ActivitySplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysplashscreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent tohome;
                tohome = new Intent(ActivitySplashScreen.this,ActivityStart.class);
                startActivity(tohome);
                finish();
            }
        },5000);

    }



}