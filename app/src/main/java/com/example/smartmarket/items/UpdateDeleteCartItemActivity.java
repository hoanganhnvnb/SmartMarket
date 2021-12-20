package com.example.smartmarket.items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.ApiService;
import com.example.main.MarketApp;
import com.example.models.CartItems;
import com.example.models.Items;
import com.example.models.MessageApi;
import com.example.smartmarket.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeleteCartItemActivity extends AppCompatActivity {

    FloatingActionButton ud_backbtn;
    ImageView ud_image;
    TextView ud_title, ud_des, ud_priceSell, ud_quantity_sold, ud_quantity, ud_quantity_ud;
    Button ud_button;
    ImageView ud_minus, ud_plus;

    private Items item;
    private CartItems cartItems;
    private int quantityItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_cart_item);
        
        initView();
        getCartItemBundle();
    }

    private boolean getCartItemBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return false;
        }
        cartItems = (CartItems) bundle.get("object_crartitem");
        item = cartItems.items;

        ud_title.setText(item.title);
        ud_des.setText(item.description);
        ud_priceSell.setText(String.valueOf(item.sellPrice));
        ud_quantity_sold.setText(String.valueOf(item.quantity_sold));
        ud_quantity.setText(String.valueOf(item.quantity));

        quantityItem = cartItems.quantity;
        ud_quantity_ud.setText(String.valueOf(quantityItem));

        String imgUrl = MarketApp.API_ROOT_URL + item.image;
        Picasso.with(this)
                .load(imgUrl)
                .into(ud_image);

        return true;
    }

    private void initView() {
        ud_backbtn = findViewById(R.id.ud_backbtn);
        ud_image = findViewById(R.id.ud_image);
        ud_title = findViewById(R.id.ud_title);
        ud_des = findViewById(R.id.ud_des);
        ud_priceSell = findViewById(R.id.ud_priceSell);
        ud_quantity_sold = findViewById(R.id.ud_quantity_sold);
        ud_quantity = findViewById(R.id.ud_quantity);
        ud_quantity_ud = findViewById(R.id.ud_quantity_ud);
        ud_button = findViewById(R.id.ud_button);
        ud_minus = findViewById(R.id.ud_minus);
        ud_plus = findViewById(R.id.ud_plus);

        ud_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickMinus();
            }
        });

        ud_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPlus();
            }
        });

        ud_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEditUpdate();
            }
        });

        ud_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void clickPlus() {
        if (this.quantityItem + 1 > item.quantity) {
            Toast.makeText(UpdateDeleteCartItemActivity.this, "Số lượng đạt tối đa", Toast.LENGTH_SHORT).show();
        } else {
            this.quantityItem = this.quantityItem + 1;
            ud_quantity_ud.setText(String.valueOf(quantityItem));
        }
    }

    private void clickMinus() {
        if (this.quantityItem - 1 < 0) {
            Toast.makeText(UpdateDeleteCartItemActivity.this, "Số lượng đạt tối thiểu", Toast.LENGTH_SHORT).show();
        } else {
            this.quantityItem = this.quantityItem - 1;
            ud_quantity_ud.setText(String.valueOf(quantityItem));
        }
    }

    private void clickEditUpdate() {

        if (quantityItem == 0) {
            openDialog(Gravity.BOTTOM);
        } else {
            ApiService.apiService.updateCartItem(cartItems.id, cartItems.cart, cartItems.items.id, quantityItem).enqueue(new Callback<MessageApi>() {
                @Override
                public void onResponse(Call<MessageApi> call, Response<MessageApi> response) {
                    Toast.makeText(UpdateDeleteCartItemActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

                @Override
                public void onFailure(Call<MessageApi> call, Throwable t) {

                }
            });
        }
    }

    private void openDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_confirm_delete);
        Window window = dialog.getWindow();

        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        AppCompatButton accept = dialog.findViewById(R.id.confirm_delete);
        AppCompatButton reject = dialog.findViewById(R.id.reject_delete);

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.apiService.deleteCartItem(cartItems.id).enqueue(new Callback<MessageApi>() {
                    @Override
                    public void onResponse(Call<MessageApi> call, Response<MessageApi> response) {
                        Toast.makeText(UpdateDeleteCartItemActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                        onBackPressed();
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