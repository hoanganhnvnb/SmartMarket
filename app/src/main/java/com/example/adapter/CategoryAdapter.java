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

import com.example.models.Category;
import com.example.smartmarket.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<Category> categories;

    public CategoryAdapter(ArrayList<Category> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_category, parent, false);

        return new ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.category_name.setText(category.title);

        String imgUrl = category.image;
        int choiceBG = position % 5;

        switch (choiceBG) {
            case 0: {
                holder.category_layout.setBackground(ContextCompat
                        .getDrawable(holder.itemView.getContext(), R.drawable.category_bg1));
                break;
            }
            case 1: {
                holder.category_layout.setBackground(ContextCompat
                        .getDrawable(holder.itemView.getContext(), R.drawable.category_bg2));
                break;
            }
            case 2: {
                holder.category_layout.setBackground(ContextCompat
                        .getDrawable(holder.itemView.getContext(), R.drawable.category_bg3));
                break;
            }
            case 3: {
                holder.category_layout.setBackground(ContextCompat
                        .getDrawable(holder.itemView.getContext(), R.drawable.category_bg4));
                break;
            }
            case 4: {
                holder.category_layout.setBackground(ContextCompat
                        .getDrawable(holder.itemView.getContext(), R.drawable.category_bg5));
                break;
            }
        }

//        int drawableResourceId = holder.itemView.getContext().getResources()
//                .getIdentifier(imgUrl, "drawable", holder.itemView.getContext().getPackageName());

        Picasso.with(holder.itemView.getContext())
                .load(imgUrl)
                .into(holder.category_image);

    }


    @Override
    public int getItemCount() {
        if (categories == null) {
            return 0;
        }
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView category_name;
        ImageView category_image;
        ConstraintLayout category_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category_name = itemView.findViewById(R.id.category_name);
            category_image = itemView.findViewById(R.id.category_image);
            category_layout = itemView.findViewById(R.id.category_layout);
        }
    }

}
