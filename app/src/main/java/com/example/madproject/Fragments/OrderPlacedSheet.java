package com.example.madproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.madproject.ActivityMain;
import com.example.madproject.Fragments.Home;
import com.example.madproject.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class OrderPlacedSheet extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderplacedsheet, container, false);

        Button GoHome = view.findViewById(R.id.btn_gotohome); // Your button ID in XML

        GoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(getActivity(), ActivityMain.class);
                startActivity(gohome);
                dismiss(); // Close the BottomSheet
            }
        });

        return view;
    }
}
