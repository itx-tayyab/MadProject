package com.example.madproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.madproject.Activityeachcategory;
import com.example.madproject.Modals.modalmenurecyclerview;
import com.example.madproject.R;

import java.util.ArrayList;

public class
adaptermenurecyclerview extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<modalmenurecyclerview> itemList;
    private final Context context;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public adaptermenurecyclerview(Context context, ArrayList<modalmenurecyclerview> itemList) {
        this.context = context;
        this.itemList = itemList != null ? itemList : new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).isHeader() ? TYPE_HEADER : TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activitymenuheader, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activitymenurecyclerview, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        modalmenurecyclerview item = itemList.get(position);

        if (getItemViewType(position) == TYPE_HEADER) {
            ((HeaderViewHolder) holder).sectionTitle.setText(item.getItemName());
        } else {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.itemName.setText(item.getItemName());
            itemHolder.itemDescription.setText(item.getItemDescription());
            itemHolder.itemPrice.setText("Rs. " + item.getItemPrice());

            String imageUrl = item.getImageUrl();

            if (!TextUtils.isEmpty(imageUrl)) {
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.placeholder)
                        .into(itemHolder.itemImage);
            } else {
                itemHolder.itemImage.setImageResource(R.drawable.placeholder);
            }

            // Set click listener to open Activityeachcategory
            itemHolder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, Activityeachcategory.class);
                intent.putExtra("itemName", item.getItemName());
                intent.putExtra("itemDescription", item.getItemDescription());
                intent.putExtra("itemPrice", item.getItemPrice());
                intent.putExtra("image_url", item.getImageUrl());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView sectionTitle;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            sectionTitle = itemView.findViewById(R.id.headerTextView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemDescription, itemPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.img1);
            itemName = itemView.findViewById(R.id.text1);
            itemDescription = itemView.findViewById(R.id.text2);
            itemPrice = itemView.findViewById(R.id.text3);
        }
    }
}
