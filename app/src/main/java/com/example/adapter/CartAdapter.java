package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Cart;
import com.example.smartmarket.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> arrayCart;

    public CartAdapter(Context context, ArrayList<Cart> arrayCart) {
        this.context = context;
        this.arrayCart = arrayCart;
    }

    @Override
    public int getCount() {
        return arrayCart.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayCart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public static class ViewHolder {
        public TextView txtNameCart, txtPriceCart;
        public ImageView imageCart;
        public Button btnMinus, btnValues, btnPlus;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)  context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_cart, null);
            viewHolder.txtNameCart = (TextView) view.findViewById(R.id.name_cart);
            viewHolder.txtPriceCart = (TextView) view.findViewById(R.id.price_cart);
            viewHolder.imageCart = (ImageView) view.findViewById(R.id.imageview_cart);
            viewHolder.btnMinus = (Button) view.findViewById(R.id.button_minus);
            viewHolder.btnValues = (Button) view.findViewById(R.id.button_values);
            viewHolder.btnPlus = (Button) view.findViewById(R.id.button_plus);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Cart cart = (Cart) getItem(i);
        viewHolder.txtNameCart.setText(cart.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtPriceCart.setText(decimalFormat.format(cart.getPrice()) + "Đ");
        Picasso.with(context).load(cart.getPicture())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.error)
                .into(viewHolder.imageCart);
        viewHolder.btnValues.setText(cart.getQuantity() + "");
        return view;
    }
}
