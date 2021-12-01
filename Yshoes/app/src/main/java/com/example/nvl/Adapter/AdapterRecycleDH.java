package com.example.nvl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nvl.Class.DonHang;
import com.example.nvl.Class.Product;
import com.example.nvl.Class.ProductCart;
import com.example.nvl.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRecycleDH extends RecyclerView.Adapter<AdapterRecycleDH.ViewHolder> {
    List<ProductCart> donHangList;
    Context context;

    public AdapterRecycleDH(List<ProductCart> donHangList, Context context) {
        this.donHangList = donHangList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_itemdonhang,parent,false);
         ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv.setText(donHangList.get(position).getName());
        holder.tvnumber.setText("Số lượng: "+donHangList.get(position).getNumber());
        Picasso.with(context).load(donHangList.get(position).getImg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tv ;
        TextView tvnumber ;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            img = itemView.findViewById(R.id.imgItemDonhang);
            tv = itemView.findViewById(R.id.tvNameItemDonHang);
            tvnumber = itemView.findViewById(R.id.tvNumberItemDonHang);
        }
    }
}
