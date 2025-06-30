package com.example.madproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.example.madproject.Adapters.adapterrecyclerview;
import com.example.madproject.Modals.modalrecyclerview;
import com.example.madproject.R;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private RecyclerView recyclerView;
    private adapterrecyclerview recyclerAdapter;
    private ArrayList<modalrecyclerview> list;
    private DatabaseReference dbRef;
    private TextView ViewAll;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

        ViewAll = view.findViewById(R.id.viewall);
        recyclerView = view.findViewById(R.id.recyclerView);
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);

        // Slider images
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slider5, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider3, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        list = new ArrayList<>();
        recyclerAdapter = new adapterrecyclerview(list, getContext(), () -> openMenuFragment());
        recyclerView.setAdapter(recyclerAdapter);

        dbRef = FirebaseDatabase.getInstance().getReference().child("menu_category");
        fetchDataFromFirebase();

        ViewAll.setOnClickListener(v -> openMenuFragment());

        return view;
    }

    private void fetchDataFromFirebase() {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    String categoryName = child.getKey();
                    String imageUrl = child.getValue(String.class);
                    if (categoryName != null && imageUrl != null) {
                        list.add(new modalrecyclerview(imageUrl, categoryName));
                    }
                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void openMenuFragment() {
        Fragment menuFragment = new Menu();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.bottomframelayout, menuFragment)
                .addToBackStack(null)
                .commit();
    }
}
