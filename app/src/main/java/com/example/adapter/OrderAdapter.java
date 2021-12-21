package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.models.Order;
import com.example.smartmarket.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    ArrayList<Order> orders;
    Context context;

    public OrderAdapter(Context context, ArrayList<Order> orders) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_order, parent, false);
        return new OrderAdapter.ViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderHistory_item.setText(convertDateTime(order.create_at));
        holder.order_total_rv.setText(String.valueOf(order.order_total));
        holder.layout_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDetail(order);
            }
        });
    }

    private void onClickDetail(Order order) {

    }


    @Override
    public int getItemCount() {
        if (orders == null) {
            return 0;
        }
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderHistory_item ;
        TextView order_total_rv;
        ConstraintLayout layout_order;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderHistory_item = itemView.findViewById(R.id.orderHistory_item);
            order_total_rv = itemView.findViewById(R.id.order_total_rv);
            layout_order = itemView.findViewById(R.id.layout_order);
        }
    }

    private String convertDateTime(String date){
        String[] parts = date.split("T",2);
        return parts[0];
    }
}
