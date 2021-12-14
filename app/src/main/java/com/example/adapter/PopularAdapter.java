package com.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Items;
import com.example.smartmarket.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    ArrayList<Items> itemsPopular;

    public PopularAdapter(ArrayList<Items> itemsPopular) {
        this.itemsPopular = itemsPopular;
    }

    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_popular, parent, false);
        return new ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
        Items item = itemsPopular.get(position);
        holder.popular_title.setText(item.title);
        holder.popular_price_sell.setText(String.valueOf(item.sellPrice));
        holder.popular_quantity_sold.setText(String.valueOf(item.quantity_sold));

        String imgUrl = item.image;
        Picasso.with(holder.itemView.getContext())
                .load(imgUrl)
                .into(holder.popular_image);


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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popular_title = itemView.findViewById(R.id.popular_title);
            popular_image = itemView.findViewById(R.id.popular_image);
            popular_price_sell = itemView.findViewById(R.id.popular_price_sell);
            popular_quantity_sold = itemView.findViewById(R.id.popular_quantity_sold);
        }
    }
}
