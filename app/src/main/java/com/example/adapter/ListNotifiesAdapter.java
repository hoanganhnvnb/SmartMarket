package com.example.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api.ApiService;
import com.example.models.MessageApi;
import com.example.models.Notify;
import com.example.smartmarket.R;
import com.example.smartmarket.notification.NotificationActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNotifiesAdapter extends RecyclerView.Adapter<ListNotifiesAdapter.NotifyViewHolder> {
    ArrayList<Notify> listNotifies;
    Context context;

    public ListNotifiesAdapter(Context c, ArrayList<Notify> listNotifies) {
        this.context = c;
        this.listNotifies = listNotifies;
    }

    @NonNull
    @Override
    public ListNotifiesAdapter.NotifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflater = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_notify, parent, false);
        return new NotifyViewHolder(inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ListNotifiesAdapter.NotifyViewHolder holder, int position) {
        Notify notify = listNotifies.get(position);
        holder.notify_title.setText(notify.title);
        holder.notify_content.setText(notify.content);
        holder.notify_create_at.setText(notify.create_at);
        if(notify.is_read == true) {
            holder.notify_is_read.setImageResource(R.drawable.ic_is_read_notify);
        } else {
            holder.notify_is_read.setImageResource(R.drawable.ic_is_not_read_notify);
        }

        holder.notify_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.notify_is_read.setImageResource(R.drawable.ic_is_read_notify);
                onClickNotify(notify.id);
            }
        });

        holder.delete_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder((NotificationActivity) context);
                builder.setMessage("Xác nhận xóa thông báo?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listNotifies.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyItemRangeChanged(holder.getAdapterPosition(), listNotifies.size());
                        ApiService.apiService.deleteNotification(notify.id).enqueue(new Callback<MessageApi>() {
                            @Override
                            public void onResponse(Call<MessageApi> call, Response<MessageApi> response) {
                            }

                            @Override
                            public void onFailure(Call<MessageApi> call, Throwable t) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });
    }

    private void onClickNotify(int id) {
        ApiService.apiService.markIsReadNotify(id).enqueue(new Callback<MessageApi>() {
            @Override
            public void onResponse(Call<MessageApi> call, Response<MessageApi> response) {
            }

            @Override
            public void onFailure(Call<MessageApi> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (listNotifies == null) {
            return 0;
        }
        return listNotifies.size();
    }

    public class NotifyViewHolder extends RecyclerView.ViewHolder {
        TextView notify_title;
        TextView notify_content;
        TextView notify_create_at;
        ImageView notify_is_read;
        LinearLayout notify_layout;
        ImageButton delete_notify;

        public NotifyViewHolder(@NonNull View itemView) {
            super(itemView);

            notify_title = itemView.findViewById(R.id.rv_notify_title);
            notify_content = itemView.findViewById(R.id.rv_notify_content);
            notify_create_at = itemView.findViewById(R.id.rv_notify_time);
            notify_is_read = itemView.findViewById(R.id.rv_icon_read_notify);
            notify_layout = itemView.findViewById(R.id.notify_layout);
            delete_notify = itemView.findViewById(R.id.notify_delete);
        }
    }
}

