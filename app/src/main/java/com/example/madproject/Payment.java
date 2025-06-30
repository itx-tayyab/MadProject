package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madproject.Fragments.OrderPlacedSheet;
import com.example.madproject.Modals.ModalPayment;
import com.example.madproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Payment extends AppCompatActivity {

    TextView payname, payemail, payphone, payaddress, paytotal;
    ImageView  BackButton;

    Button PlaceMyOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        payname = findViewById(R.id.payname);
        payemail = findViewById(R.id.payemail);
        payphone = findViewById(R.id.payphone);
        payaddress = findViewById(R.id.payaddress);
        paytotal = findViewById(R.id.paytotalamount);
        BackButton = findViewById(R.id.backbutton);
        PlaceMyOrder = findViewById(R.id.btn_placemyorder);

        BackButton.setOnClickListener(v -> onBackPressed());

        PlaceMyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);
                DatabaseReference pendingRef = FirebaseDatabase.getInstance().getReference("PendingOrders").child(uid);

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            // Collect data to move to PendingOrders
                            Map<String, Object> orderData = new HashMap<>();

                            if (snapshot.child("CartItem").exists()) {
                                orderData.put("CartItem", snapshot.child("CartItem").getValue());
                            }

                            orderData.put("name", snapshot.child("name").getValue());
                            orderData.put("phone", snapshot.child("phone").getValue());
                            orderData.put("address", snapshot.child("address").getValue());
                            orderData.put("username", snapshot.child("username").getValue());
                            orderData.put("totalAmount", snapshot.child("totalAmount").getValue());

                            // Save to PendingOrders
                            pendingRef.setValue(orderData).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Clean up user's cart and total
                                    userRef.child("CartItem").removeValue();
                                    userRef.child("totalAmount").removeValue();

                                    // Show the order placed bottom sheet
                                    OrderPlacedSheet bottomSheet = new OrderPlacedSheet();
                                    bottomSheet.show(getSupportFragmentManager(), "OrderPlacedSheet");

                                    Toast.makeText(Payment.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Payment.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(Payment.this, "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Payment.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });




        fetchUserData();
        calculateTotalAmount(); // New method
    }

    private void fetchUserData() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModalPayment modal = snapshot.getValue(ModalPayment.class);

                if (modal != null) {
                    payname.setText(modal.getName());
                    payemail.setText(modal.getUsername());
                    payphone.setText(modal.getPhone());
                    payaddress.setText(modal.getAddress());
                } else {
                    Toast.makeText(Payment.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Payment.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculateTotalAmount() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uid);
        DatabaseReference cartRef = userRef.child("CartItem");

        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int total = 0;

                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Integer price = itemSnapshot.child("itemPrice").getValue(Integer.class);
                    Integer quantity = itemSnapshot.child("quantity").getValue(Integer.class);

                    if (price != null && quantity != null) {
                        total += price * quantity;
                    }
                }

                // Set to TextView
                paytotal.setText("Total: Rs " + total);


                // ✅ Save totalAmount back to Firebase so it's available during Place Order
                userRef.child("totalAmount").setValue(total);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Payment.this, "Failed to calculate total: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
