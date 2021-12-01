package com.example.nvl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nvl.Class.ProductCart;
import com.example.nvl.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterItemDonHang extends BaseAdapter {
    List<ProductCart> cartList;
    Context context;

    public AdapterItemDonHang(List<ProductCart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_itemdonhang, parent, false);
        ImageView img = view.findViewById(R.id.imgItemDonhang);
        TextView tv = view.findViewById(R.id.tvNameItemDonHang);
        TextView tvnumber = view.findViewById(R.id.tvNumberItemDonHang);
        tv.setText(cartList.get(position).getName());
        tvnumber.setText("Số lượng: "+cartList.get(position).getNumber());
        Picasso.with(context).load(cartList.get(position).getImg()).into(img);
        return view;
    }
}
