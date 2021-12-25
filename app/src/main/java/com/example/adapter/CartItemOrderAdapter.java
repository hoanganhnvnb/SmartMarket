package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.CartItems;
import com.example.models.Items;
import com.example.smartmarket.R;
import com.example.smartmarket.items.UpdateDeleteCartItemActivity;

import java.util.ArrayList;

public class CartItemOrderAdapter extends RecyclerView.Adapter<CartItemOrderAdapter.ViewHolder> {
    ArrayList<CartItems> cartItems;
    Context context;

    public CartItemOrderAdapter(Context context, ArrayList<CartItems> cartItems) {
        this.cartItems = cartItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CartItemOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_cartitem_order, parent, false);
        return new CartItemOrderAdapter.ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemOrderAdapter.ViewHolder holder, int position) {
        CartItems cart_item = cartItems.get(position);
        Items item = cart_item.items;

        holder.title_item_order.setText(item.title);
        holder.quantity_item_order.setText("x" + String.valueOf(cart_item.quantity));
        holder.price_item_order.setText(String.valueOf(cart_item.quantity * item.sellPrice) + "đ");

        holder.layout_item_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCartItem(cart_item);
            }
        });
    }

    private void clickCartItem(CartItems cart_item) {
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
        TextView quantity_item_order;
        TextView title_item_order;
        TextView price_item_order;
        ConstraintLayout layout_item_order;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            quantity_item_order = itemView.findViewById(R.id.quantity_item_order);
            title_item_order = itemView.findViewById(R.id.title_item_order);
            price_item_order = itemView.findViewById(R.id.price_item_order);
            layout_item_order = itemView.findViewById(R.id.layout_item_order);
        }
    }
}
