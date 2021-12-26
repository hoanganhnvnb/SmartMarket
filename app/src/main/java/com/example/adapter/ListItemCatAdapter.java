package com.example.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.main.MarketApp;
import com.example.models.Items;
import com.example.smartmarket.R;
import com.example.smartmarket.items.DetailItemActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListItemCatAdapter extends RecyclerView.Adapter<ListItemCatAdapter.ItemViewHolder> {

    ArrayList<Items> listItems;
    Context context;

    public ListItemCatAdapter(Context c, ArrayList<Items> listItems) {
        this.context = c;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ListItemCatAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);
        return new ListItemCatAdapter.ItemViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemCatAdapter.ItemViewHolder holder, int position) {
        Items item = listItems.get(position);
        String imgUrl = MarketApp.API_ROOT_URL + item.image;
        holder.item_title.setText(item.title);
        holder.item_des.setMaxLines(1);
        holder.item_des.setEllipsize(TextUtils.TruncateAt.END);
        holder.item_des.setText(item.description);
        holder.item_quantity.setText("Số lượng: " + String.valueOf(item.quantity));
        Picasso.with(holder.itemView.getContext())
                .load(imgUrl)
                .into(holder.item_image);
        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDetail(item);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (listItems == null) {
            return 0;
        }
        return listItems.size();
    }

    private void onClickDetail(Items item) {
        Intent intent = new Intent(context, DetailItemActivity.class);
        Bundle bundle = new Bundle();
        item.image = MarketApp.API_ROOT_URL + item.image;
        bundle.putSerializable("object_item", item);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView item_image;
        TextView item_title;
        TextView item_des;
        TextView item_quantity;
        LinearLayout item_layout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            item_image = itemView.findViewById(R.id.rv_item_image);
            item_title = itemView.findViewById(R.id.rv_item_title);
            item_des = itemView.findViewById(R.id.rv_item_des);
            item_quantity = itemView.findViewById(R.id.rv_item_quantity);
            item_layout = itemView.findViewById(R.id.item_layout);
        }
    }

}
