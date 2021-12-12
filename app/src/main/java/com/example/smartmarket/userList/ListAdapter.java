package com.example.smartmarket.userList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.models.User;
import com.example.smartmarket.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<User> {

    public  ListAdapter(Context context, ArrayList<User> userArrayList){
        super(context,R.layout.list_user,userArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        User user = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_user,parent,false);
        }

        TextView userName = convertView.findViewById(R.id.list_userName);
        TextView userMail = convertView.findViewById(R.id.list_userMail);

        userName.setText(user.username);
        userMail.setText(user.email);

        return super.getView(position, convertView, parent);
    }
}
