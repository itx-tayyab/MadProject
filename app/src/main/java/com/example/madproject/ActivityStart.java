package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityStart extends AppCompatActivity {
    Button Start;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Intent intent = new Intent(ActivityStart.this, ActivityMain.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activitystart);
            Start = findViewById(R.id.startactivity);
            Start.setOnClickListener(v -> {
                Intent tologin = new Intent(ActivityStart.this, Activityloginpage.class);
                startActivity(tologin);
                finish();
            });
        }
    }
}
