package com.example.madproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.madproject.Modals.modalrecyclerview;
import com.example.madproject.R;

import java.util.ArrayList;

public class adapterrecyclerview extends RecyclerView.Adapter<adapterrecyclerview.ViewHolder> {

    private ArrayList<modalrecyclerview> list;
    private Context context;
    private OnItemClickListener listener;

    // Interface to handle item clicks
    public interface OnItemClickListener {
        void onItemClick();
    }

    // Constructor
    public adapterrecyclerview(ArrayList<modalrecyclerview> list, Context context, OnItemClickListener listener) {
        this.list = (list != null) ? list : new ArrayList<>();
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activitysamplerecyclerview, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        modalrecyclerview item = list.get(position);

        Glide.with(context)
                .load(item.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.img);

        holder.text.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView text;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            text = itemView.findViewById(R.id.text);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick();  // Trigger fragment switch
                }
            });
        }
    }
}
