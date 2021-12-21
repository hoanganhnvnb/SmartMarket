package com.example.smartmarket.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.CartItemOrderAdapter;
import com.example.api.ApiService;
import com.example.models.CartItems;
import com.example.models.MessageApi;
import com.example.models.Order;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.dashboard.DashboardActivity;
import com.example.smartmarket.items.UpdateDeleteCartItemActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends Base {
    TextView order_name_user, order_id, order_is_complete, cart_totalOrder;

    RecyclerView.Adapter adapter;
    RecyclerView order_cartItem_rvOrder;

    Button cart_payButtonOrder, cart_backButtonOrder;
    RadioButton radio_cash, radio_qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();
        createOrder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApiService.apiService.getCartItems(app.mCart.id).enqueue(new Callback<ArrayList<CartItems>>() {
            @Override
            public void onResponse(Call<ArrayList<CartItems>> call, Response<ArrayList<CartItems>> response) {
                ArrayList<CartItems> cartItems = response.body();
                if (cartItems != null) {
                    app.cartItems = cartItems;
                    adapter = new CartItemOrderAdapter(OrderActivity.this, app.cartItems);
                    order_cartItem_rvOrder.setAdapter(adapter);
                    int total = 0;
                    for (int i = 0; i < app.cartItems.size(); i++) {
                        CartItems cartItem = app.cartItems.get(i);
                        total = total + cartItem.quantity * cartItem.items.sellPrice;
                    }
                    cart_totalOrder.setText(String.valueOf(total));
                }
                if (app.mOrder != null) {
                    ApiService.apiService.updateTotalOrder(app.mOrder.id).enqueue(new Callback<MessageApi>() {
                        @Override
                        public void onResponse(Call<MessageApi> call, Response<MessageApi> response) {
                        }

                        @Override
                        public void onFailure(Call<MessageApi> call, Throwable t) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CartItems>> call, Throwable t) {

            }
        });


    }

    private void initView() {
        order_name_user = findViewById(R.id.order_name_user);
        order_id = findViewById(R.id.order_id);
        order_is_complete = findViewById(R.id.order_is_complete);
        cart_totalOrder = findViewById(R.id.cart_totalOrder);
        cart_payButtonOrder = findViewById(R.id.cart_payButtonOrder);
        radio_cash = findViewById(R.id.radio_cash);
        radio_qrcode = findViewById(R.id.radio_qrcode);
        cart_backButtonOrder = findViewById(R.id.cart_backButtonOrder);

        cart_backButtonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        order_cartItem_rvOrder = findViewById(R.id.order_cartItem_rvOrder);
        order_cartItem_rvOrder.setLayoutManager(linearLayoutManager);

        cart_payButtonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPay();
            }
        });
    }

    private void onClickPay() {
        if (radio_cash.isChecked()) {
            Toast.makeText(this, "Tiền mặt", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Quét máng", Toast.LENGTH_SHORT).show();
        }
    }

    private void createOrder() {
        order_name_user.setText(app.mUser.first_name + " " + app.mUser.last_name);

        String des = "Hóa đơn của " + app.mUser.username;
        ApiService.apiService.createOrder(app.mCart.id, app.mUser.id, des).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Order order = response.body();
                if (order != null) {
                    app.mOrder = order;
                    order_id.setText(String.valueOf(order.id));
                    if (app.mOrder.is_completed) {
                        order_is_complete.setText("Đã thanh toán");
                    } else {
                        order_is_complete.setText("Chưa thanh toán");
                    }
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        openDialog();
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_confirm_delete);
        Window window = dialog.getWindow();

        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);

        AppCompatButton accept = dialog.findViewById(R.id.confirm_delete);
        AppCompatButton reject = dialog.findViewById(R.id.reject_delete);
        TextView text_dialog = dialog.findViewById(R.id.text_dialog);
        text_dialog.setText("Quay lại sẽ hủy bỏ hóa đơn?");

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.apiService.deleteOrder(app.mOrder.id).enqueue(new Callback<MessageApi>() {
                    @Override
                    public void onResponse(Call<MessageApi> call, Response<MessageApi> response) {
                        if (response.code() == 200) {
                            Toast.makeText(OrderActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                            app.mOrder = null;
                            dialog.dismiss();
                            OrderActivity.super.onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageApi> call, Throwable t) {

                    }
                });
            }
        });

        dialog.show();
    }
}