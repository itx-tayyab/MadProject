package com.example.madproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.madproject.Activityloginpage;
import com.example.madproject.Modals.modalaccount;
import com.example.madproject.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.EditText;
import android.widget.Button;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends Fragment {

    private GoogleSignInClient gsc;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account, container, false);

        mAuth = FirebaseAuth.getInstance();
        gsc = GoogleSignIn.getClient(requireContext(),
                new com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder(
                        com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()
        );

        EditText nameET = view.findViewById(R.id.name);
        EditText emailET = view.findViewById(R.id.email);
        EditText phoneET = view.findViewById(R.id.phone);
        EditText addressET = view.findViewById(R.id.address);
        Button btnSave = view.findViewById(R.id.btn_savedata);
        Button btnUpdate = view.findViewById(R.id.btn_update);
        Button btnLogout = view.findViewById(R.id.btn_logout);

        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

        // Load existing data if present
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    modalaccount user = snapshot.getValue(modalaccount.class);
                    if (user != null) {
                        nameET.setText(user.getName());
                        phoneET.setText(user.getPhone());
                        addressET.setText(user.getAddress());
                        emailET.setText(user.getUsername());
                        emailET.setEnabled(false); // Prevent editing email
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load user info", Toast.LENGTH_SHORT).show();
            }
        });

        View.OnClickListener saveOrUpdateListener = v -> {
            String name = nameET.getText().toString().trim();
            String email = emailET.getText().toString().trim();
            String phone = phoneET.getText().toString().trim();
            String address = addressET.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            modalaccount userInfo = new modalaccount(name, phone, address, email);
            userRef.setValue(userInfo).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Profile saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error saving profile", Toast.LENGTH_SHORT).show();
                }
            });
        };

        btnSave.setOnClickListener(saveOrUpdateListener);
        btnUpdate.setOnClickListener(saveOrUpdateListener);

        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            gsc.signOut().addOnCompleteListener(task -> {
                Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), Activityloginpage.class));
                requireActivity().finish();
            });
        });

        return view;
    }

}
