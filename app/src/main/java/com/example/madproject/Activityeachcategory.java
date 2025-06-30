package com.example.madproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class Activityeachcategory extends AppCompatActivity {

    private ImageView backButton, itemImage;
    private Button addToCartButton;
    private TextView itemNameText, itemDescriptionText, itemPriceText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityeachcategory);

        // Initialize UI elements
        backButton = findViewById(R.id.backButton);
        addToCartButton = findViewById(R.id.addToCartButton);
        itemNameText = findViewById(R.id.itemNameText);
        itemImage = findViewById(R.id.itemImage);
        itemDescriptionText = findViewById(R.id.itemDescriptionText);
        itemPriceText = findViewById(R.id.itemPriceText);

        // Get data from intent
        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        String itemDescription = intent.getStringExtra("itemDescription");
        long itemPrice = intent.getLongExtra("itemPrice", 0L);
        String imageUrl = intent.getStringExtra("image_url");

        // Set data to UI
        itemNameText.setText(itemName);
        itemDescriptionText.setText(itemDescription);
        itemPriceText.setText("Rs. " + itemPrice);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(itemImage);
        } else {
            itemImage.setImageResource(R.drawable.placeholder);
        }

        // Handle back button click
        backButton.setOnClickListener(v -> onBackPressed());

        // Handle Add to Cart button click
        addToCartButton.setOnClickListener(v -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user == null) {
                Toast.makeText(this, "Please log in first!", Toast.LENGTH_SHORT).show();
                return; // Prevent further execution if not logged in
            }

            String uid = user.getUid();
            android.util.Log.d("CART_DEBUG", "Using UID: " + uid); // Debug log

            DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(uid)
                    .child("CartItem")
                    .child(itemName);

            Map<String, Object> cartItem = new HashMap<>();
            cartItem.put("itemName", itemName);
            cartItem.put("itemDescription", itemDescription);
            cartItem.put("itemPrice", itemPrice);
            cartItem.put("image_url", imageUrl);

            cartRef.setValue(cartItem).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(Activityeachcategory.this, "Added to cart: " + itemName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Activityeachcategory.this, "Failed to add to cart", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}
