package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Activitysignuppage extends AppCompatActivity {

    EditText Email, Password;
    Button Register, Googlebtn;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    TextView login;

    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignupActivity";

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.d(TAG, "User already signed in: " + currentUser.getEmail());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysignuppage);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Register = findViewById(R.id.btn_signup);
        login = findViewById(R.id.btn_login);
        Googlebtn = findViewById(R.id.btn_signup_google);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Activitysignuppage.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Activitysignuppage.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Activitysignuppage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Signup success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    if (user != null) {
                                        String uid = user.getUid();

                                        HashMap<String, String> userData = new HashMap<>();
                                        userData.put("username", email);
                                        userData.put("password", password); // Not secure, just for test

                                        database.getReference().child("users").child(uid).setValue(userData);

                                        Toast.makeText(Activitysignuppage.this, "Account created successfully!", Toast.LENGTH_SHORT).show();

                                        Intent signup = new Intent(Activitysignuppage.this, ActivityMain.class);
                                        startActivity(signup);
                                        finish();
                                    }
                                } else {
                                    Log.e(TAG, "Signup failed: " + task.getException());
                                    Toast.makeText(Activitysignuppage.this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(Activitysignuppage.this, gso);

        Googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText("signup").Toast.Short_Length.Show()
                Intent login = new Intent(Activitysignuppage.this, Activityloginpage.class);
                startActivity(login);
                finish();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    Auth(account.getIdToken());
                }
            } catch (ApiException e) {
                Toast.makeText(this, "Google sign-in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void Auth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(Activitysignuppage.this, "Google Sign-In Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Activitysignuppage.this, ActivityMain.class));
                        finish();
                    } else {
                        Toast.makeText(Activitysignuppage.this, "Google Sign-In Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
