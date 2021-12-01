package com.example.nvl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nvl.Class.Product;
import com.example.nvl.Interface.ISendData;
import com.example.nvl.Interface.ItemClickListener;
import com.example.nvl.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder>  {
    List<Product> productList;
    Context context;
    ISendData iSendData;
    ItemClickListener itemClickListener;

    public AdapterRecyclerView(List<Product> productList, Context context,ItemClickListener itemClickListener) {
        this.productList = productList;
        this.context = context;
        this.itemClickListener=itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recyclerview_newproduct,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product = productList.get(position);
        Picasso.with(context).load(product.getImg()).into(holder.img);
        holder.tvName.setText(product.getName());
        holder.tvMoney.setText(product.getMoney()+" đ");
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName;
        TextView tvMoney;
        LinearLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.layoutproduct);
            img =itemView.findViewById(R.id.imgNewproduct);
            tvName=itemView.findViewById(R.id.tvName);
            tvMoney=itemView.findViewById(R.id.tvMoney);
        }
    }

}
