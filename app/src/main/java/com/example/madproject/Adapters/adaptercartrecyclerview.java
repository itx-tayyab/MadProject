package com.example.madproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.madproject.Modals.modalcartrecyclerview;
import com.example.madproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class adaptercartrecyclerview extends RecyclerView.Adapter<adaptercartrecyclerview.CartViewHolder> {

    private Context context;
    private List<modalcartrecyclerview> cartItemList;

    // Constructor
    public adaptercartrecyclerview(Context context, List<modalcartrecyclerview> cartItemList) {
        this.context = context;
        this.cartItemList = cartItemList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout for the cart
        View view = LayoutInflater.from(context).inflate(R.layout.activitycartrecyclerview, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        modalcartrecyclerview item = cartItemList.get(position);

        // Load the image using Glide
        if (item.getImageUrl() != null && !item.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(item.getImageUrl()) // Load image URL from Firebase
                    .placeholder(R.drawable.placeholder) // Show placeholder if image URL is not available
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.placeholder); // Default placeholder
        }

        // Set the title, description, price, and quantity
        holder.title.setText(item.getItemName()); // Use itemName for title
        holder.subtitle.setText(item.getItemDescription()); // Use itemDescription for subtitle
        holder.price.setText("Rs " + item.getItemPrice()); // Set itemPrice (you can format it as needed)
        holder.quantity.setText(String.valueOf(item.getQuantity())); // Display quantity

        // Handle the increment and decrement of the quantity
        holder.btnPlus.setOnClickListener(v -> {
            int currentQty = item.getQuantity();
            item.setQuantity(currentQty + 1); // Increase quantity
            updateQuantityInFirebase(item);   // Optional: sync with Firebase
            notifyItemChanged(position);
        });

        holder.btnMinus.setOnClickListener(v -> {
            int currentQty = item.getQuantity();
            if (currentQty > 1) {
                item.setQuantity(currentQty - 1); // Decrease quantity
                updateQuantityInFirebase(item);   // Optional: sync with Firebase
                notifyItemChanged(position);
            }
        });


        // Handle item deletion
        holder.btnDelete.setOnClickListener(v -> {
            // Remove from Firebase
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(uid)
                    .child("CartItem")
                    .child(item.getItemName()) // Use a better unique key if possible
                    .removeValue();

            // Remove from the list
            cartItemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItemList.size());
        });
    }

    private void updateQuantityInFirebase(modalcartrecyclerview item) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance()
                .getReference("users")
                .child(uid)
                .child("CartItem")
                .child(item.getItemName()) // Consider using unique IDs instead
                .child("quantity")
                .setValue(item.getQuantity());
    }


    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    // ViewHolder class for cart items
    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, subtitle, quantity, price;
        AppCompatButton btnPlus, btnMinus, btnDelete;

        public CartViewHolder(View itemView) {
            super(itemView);

            // Initialize the views
            imageView = itemView.findViewById(R.id.img1);
            title = itemView.findViewById(R.id.text1);
            subtitle = itemView.findViewById(R.id.text2);
            price = itemView.findViewById(R.id.text4);
            quantity = itemView.findViewById(R.id.text3);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
