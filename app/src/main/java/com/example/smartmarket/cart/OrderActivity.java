package com.example.smartmarket.cart;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.CartItemOrderAdapter;
import com.example.api.ApiService;
import com.example.models.CartItems;
import com.example.models.MessageApi;
import com.example.models.Order;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.login.LoginActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends Base {
    TextView order_name_user, order_id, order_is_complete, cart_totalOrder;
    ImageView imageView;
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
            openDialogQRcode();
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

    private void openDialogQRcode() {
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
        ImageView QRcode = dialog.findViewById(R.id.img_dialog);
        text_dialog.setText("Xin chờ xác nhận");
        reject.setText("Quay lại");
        accept.setText("Tiếp tục");

        QRcode.setImageBitmap(QRcode());
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.apiService.getOrderById(app.mOrder.id).enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        Order order = response.body();
                        if (order != null) {
                            app.mOrder = order;
                            if (order.is_completed) {
                                Toast.makeText(OrderActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent( OrderActivity.this, LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                dialog.dismiss();
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(OrderActivity.this, "Chưa thanh toán đơn hàng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });


            }
        });

        dialog.show();
    }

    public Bitmap QRcode() {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(String.valueOf(app.mOrder.id), BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            return bitmap;
        } catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }
}