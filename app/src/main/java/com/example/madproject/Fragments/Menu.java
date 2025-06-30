package com.example.madproject.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madproject.Adapters.adaptermenurecyclerview;
import com.example.madproject.Adapters.adapterrecyclerview;
import com.example.madproject.Modals.modalmenurecyclerview;
import com.example.madproject.Modals.modalrecyclerview;
import com.example.madproject.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menu extends Fragment {

    private RecyclerView recyclerView;
    private adaptermenurecyclerview menuAdapter;
    private ArrayList<modalmenurecyclerview> itemList;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu, container, false);

        recyclerView = view.findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemList = new ArrayList<>();
        menuAdapter = new adaptermenurecyclerview(getContext(), itemList);
        recyclerView.setAdapter(menuAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("menu_items");
        loadMenuData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadMenuData();
    }

    @Override
    public void onStop() {
        super.onStop();
        loadMenuData();
    }

    private void loadMenuData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemList.clear();

                for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                    String categoryName = categorySnapshot.getKey();
                    itemList.add(new modalmenurecyclerview(categoryName));

                    for (DataSnapshot itemSnapshot : categorySnapshot.getChildren()) {
                        String name = itemSnapshot.child("name").getValue(String.class);
                        String description = itemSnapshot.child("description").getValue(String.class);
                        String imageUrl = itemSnapshot.child("image_url").getValue(String.class);
                        Long price = null;

                        try {
                            price = itemSnapshot.child("price").getValue(Long.class);
                        } catch (Exception e) {
                            try {
                                price = Long.parseLong(itemSnapshot.child("price").getValue(String.class));
                            } catch (Exception ex) {
                                price = 0L;
                            }
                        }

                        // Optional fields
                        String id = itemSnapshot.child("id").getValue(String.class);
                        String category = itemSnapshot.child("category").getValue(String.class);

                        // Prevent crash on missing required fields
                        if (name != null && imageUrl != null) {
                            modalmenurecyclerview item = new modalmenurecyclerview(imageUrl, name, description != null ? description : "", price);
                            itemList.add(item);
                        }


                    }
                }

                menuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error loading menu: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
