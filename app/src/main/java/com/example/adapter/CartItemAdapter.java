package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.CartItems;
import com.example.models.Items;
import com.example.smartmarket.R;
import com.example.smartmarket.items.DetailItemActivity;
import com.example.smartmarket.items.UpdateDeleteCartItemActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    ArrayList<CartItems> cartItems;
    Context context;

    public CartItemAdapter(Context context, ArrayList<CartItems> cartItems) {
        this.cartItems = cartItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_cartitem, parent, false);
        return new CartItemAdapter.ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.ViewHolder holder, int position) {
        CartItems cart_item = cartItems.get(position);
        Items item = cart_item.items;

        holder.cartitem_title.setText(item.title);
        holder.cartitem_quantity.setText(String.valueOf(cart_item.quantity));
        holder.cartitem_total.setText(String.valueOf(cart_item.quantity * item.sellPrice));

        String imgUrl = "http://18.220.110.46:8000" + item.image;
        Picasso.with(holder.itemView.getContext())
                .load(imgUrl)
                .into(holder.cartitem_image);
        
        holder.cartitem_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEditCartItem(cart_item);
            }
        });
    }

    private void clickEditCartItem(CartItems cart_item) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(context, UpdateDeleteCartItemActivity.class);
        bundle.putSerializable("object_crartitem", cart_item);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        if (cartItems == null) {
            return 0;
        }
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cartitem_image;
        TextView cartitem_title;
        ImageView cartitem_edit;
        TextView cartitem_quantity;
        TextView cartitem_total;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cartitem_image = itemView.findViewById(R.id.cartitem_image);
            cartitem_title = itemView.findViewById(R.id.cartitem_title);
            cartitem_quantity = itemView.findViewById(R.id.cartitem_quantity);
            cartitem_total = itemView.findViewById(R.id.cartitem_total);
            cartitem_edit = itemView.findViewById(R.id.cartitem_edit);
        }
    }
}
