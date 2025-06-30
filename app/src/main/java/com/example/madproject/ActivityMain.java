package com.example.madproject;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.madproject.Fragments.Account;
import com.example.madproject.Fragments.Cart;
import com.example.madproject.Fragments.Home;
//import com.example.madproject.Fragments.;  // Add this
import com.example.madproject.Fragments.Menu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;

public class ActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavbar);
        TextView Headertext = findViewById(R.id.headerText);

        // Load Home fragment by default
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.bottomframelayout, new Home())
                    .commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.navbarhome) {
                    selectedFragment = new Home();
                } else if (item.getItemId() == R.id.navbarmenu) {
                    selectedFragment = new Menu();
                } else if (item.getItemId() == R.id.navbarcart) {
                        selectedFragment = new Cart();
                } else if (item.getItemId() == R.id.navbaraccount) {
                    selectedFragment = new Account();  // Load correct fragment
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.bottomframelayout, selectedFragment)
                            .commit();
                }

                return true;
            }
        });
    }
}
