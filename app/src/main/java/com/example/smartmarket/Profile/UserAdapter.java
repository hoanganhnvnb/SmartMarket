package com.example.smartmarket.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.models.User;
import com.example.smartmarket.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserItemViewHolder> {

    private List<User> users;
    private Context context;

    public UserAdapter(List<User> users, Context c) {
        this.users = users;
        this.context = c;
    }

    @Override
    public UserItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user, parent, false);

        return new UserItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserItemViewHolder holder, int position) {
        User user = users.get(position);

        holder.username_list.setText(user.username);
        holder.name_list.setText(user.first_name + user.last_name);
        holder.email_list.setText(user.email);
    }

    public int getItemCount() {
        return users.size();
    }

    public class UserItemViewHolder extends RecyclerView.ViewHolder{
        public TextView username_list;
        public TextView email_list;
        public TextView name_list;

        public UserItemViewHolder(View itemView) {
            super(itemView);
            username_list = (TextView) itemView.findViewById(R.id.username_list);
            email_list = (TextView) itemView.findViewById(R.id.email_list);
            name_list = (TextView) itemView.findViewById(R.id.name_list);
        }
    }
}
