package com.example.nvl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nvl.Class.Product;
import com.example.nvl.Interface.ItemClickListener;
import com.example.nvl.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProduct extends BaseAdapter {
    List<Product> list;
    Context context;
    ItemClickListener itemClickListener;
    public AdapterProduct(List<Product> list, Context context,ItemClickListener itemClickListener) {
        this.list = list;
        this.context = context;
        this.itemClickListener=itemClickListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_product,parent,false);
        RelativeLayout layout = view.findViewById(R.id.relativeLayout);
        ImageView img = view.findViewById(R.id.imgItem);
        RatingBar rt = view.findViewById(R.id.rateItem);
        TextView tvNameItem = view.findViewById(R.id.tvNameItem);
        TextView tvMoneyItem = view.findViewById(R.id.tvMoneyItem);
        Picasso.with(context).load(list.get(position).getImg()).into(img);
        tvMoneyItem.setText("Giá : "+list.get(position).getMoney()+" đồng");
        tvNameItem.setText(list.get(position).getName());
        rt.setRating((float) list.get(position).getRate());
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(list.get(position));
            }
        });
        return view;
    }
}
