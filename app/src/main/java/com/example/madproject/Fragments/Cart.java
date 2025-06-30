package com.example.madproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madproject.Adapters.adaptercartrecyclerview;
import com.example.madproject.Modals.modalcartrecyclerview;
import com.example.madproject.Payment;
import com.example.madproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Fragment {

    private RecyclerView cartRecyclerView;
    private List<modalcartrecyclerview> cartItemList;
    private adaptercartrecyclerview cartAdapter;

    private FirebaseDatabase mDatabase;
    private DatabaseReference cartRef;
    private String userId;

    public Cart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.cart, container, false);

        // Initialize RecyclerView and Adapter
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartItemList = new ArrayList<>();
        cartAdapter = new adaptercartrecyclerview(getContext(), cartItemList);
        cartRecyclerView.setAdapter(cartAdapter);

        // Initialize Firebase
        mDatabase = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            userId = currentUser.getUid();
            cartRef = mDatabase.getReference("users").child(userId).child("CartItem");
            loadCartItems();
        } else {
            Log.e("CartFragment", "User not logged in.");
            // Optional: You can redirect to login or show a message
        }

        AppCompatButton btnProceed = view.findViewById(R.id.btn_proceed);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),  Payment.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadCartItems() {
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cartItemList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String image_url = snapshot.child("image_url").getValue(String.class);
                    String itemName = snapshot.child("itemName").getValue(String.class);
                    String itemDescription = snapshot.child("itemDescription").getValue(String.class);
                    Double itemPriceVal = snapshot.child("itemPrice").getValue(Double.class);
                    Integer quantityVal = snapshot.child("quantity").getValue(Integer.class);

                    // Handle null values with fallback
                    image_url = (image_url != null) ? image_url : "";
                    itemName = (itemName != null) ? itemName : "Unnamed Item";
                    itemDescription = (itemDescription != null) ? itemDescription : "";
                    double itemPrice = (itemPriceVal != null) ? itemPriceVal : 0.0;
                    int quantity = (quantityVal != null) ? quantityVal : 1;

                    modalcartrecyclerview cartItem = new modalcartrecyclerview(image_url, itemName, itemDescription, itemPrice, quantity);
                    cartItemList.add(cartItem);
                }

                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("CartFragment", "Error loading cart items: " + databaseError.getMessage());
            }
        });
    }

}
