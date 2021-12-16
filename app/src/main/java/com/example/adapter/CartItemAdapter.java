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

import com.example.models.Items;
import com.example.smartmarket.R;
import com.example.smartmarket.items.DetailItemActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    ArrayList<Items> itemsPopular;
    Context context;

    public CartItemAdapter(Context context, ArrayList<Items> itemsPopular) {
        this.itemsPopular = itemsPopular;
        this.context = context;
    }

    @NonNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_popular, parent, false);
        return new CartItemAdapter.ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.ViewHolder holder, int position) {
        Items item = itemsPopular.get(position);
        holder.popular_title.setText(item.title);
        holder.popular_price_sell.setText(String.valueOf(item.sellPrice));
        holder.popular_quantity_sold.setText(String.valueOf(item.quantity_sold));

        String imgUrl = item.image;
        Picasso.with(holder.itemView.getContext())
                .load(imgUrl)
                .into(holder.popular_image);

        holder.popular_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDetail(item);
            }
        });
    }

    private void onClickDetail(Items item) {
        Intent intent = new Intent(context, DetailItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_item", item);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        if (itemsPopular == null) {
            return 0;
        }
        return itemsPopular.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popular_image;
        TextView popular_title;
        TextView popular_price_sell;
        TextView popular_quantity_sold;
        ConstraintLayout popular_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popular_title = itemView.findViewById(R.id.popular_title);
            popular_image = itemView.findViewById(R.id.popular_image);
            popular_price_sell = itemView.findViewById(R.id.popular_price_sell);
            popular_quantity_sold = itemView.findViewById(R.id.popular_quantity_sold);
            popular_layout = itemView.findViewById(R.id.popular_layout);
        }
    }
}
