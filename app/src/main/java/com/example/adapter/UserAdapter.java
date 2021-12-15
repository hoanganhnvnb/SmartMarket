package com.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Items;
import com.example.models.User;
import com.example.smartmarket.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ArrayList<User> listUser;

    public UserAdapter(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_user, parent, false);
        return new ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = listUser.get(position);
        holder.UserName.setText(user.username);
        holder.Mail.setText(user.email);
        holder.FullName.setText(user.first_name + user.last_name);
    }

    @Override
    public int getItemCount() {
        if (listUser == null) {
            return 0;
        }
        return listUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView UserName;
        TextView Mail;
        TextView FullName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            UserName = itemView.findViewById(R.id.holder_username);
            Mail = itemView.findViewById(R.id.holder_email);
            FullName = itemView.findViewById(R.id.holder_Fullname);
        }
    }
}
