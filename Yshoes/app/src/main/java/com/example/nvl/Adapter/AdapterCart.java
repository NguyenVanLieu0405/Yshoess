package com.example.nvl.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.nvl.Class.ProductCart;
import com.example.nvl.Fragment.CartFragment;
import com.example.nvl.MainActivity;
import com.example.nvl.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCart extends BaseAdapter {
    List<ProductCart> cartList;
    Context context;
    int sl;

    public AdapterCart(List<ProductCart> cartList, Context context) {
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
        View view = inflater.inflate(R.layout.item_cart, parent, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        ImageView img = view.findViewById(R.id.imgItemCart);
        ImageView imgGiam = view.findViewById(R.id.imgGiamcart);
        ImageView imgTang = view.findViewById(R.id.imgTangcart);
        TextView tvNameItem = view.findViewById(R.id.tvNameItemCart);
        TextView tvNumberItem = view.findViewById(R.id.tvNumberItemCart);
        TextView tvMoneyItemCart = view.findViewById(R.id.tvMoneyItemCart);
        Picasso.with(context).load(cartList.get(position).getImg()).into(img);
        tvNumberItem.setText(cartList.get(position).getNumber() + "");
        tvNameItem.setText(cartList.get(position).getName());
//        int tong=cartList.get(position).getMoney()*cartList.get(position).getNumber();
        tvMoneyItemCart.setText("Đơn giá: " + cartList.get(position).getMoney() + " đ");
        imgGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl = cartList.get(position).getNumber();
                sl--;
                cartList.get(position).setNumber(sl);
                MainActivity.TONG_TIEN-=cartList.get(position).getMoney();
                databaseReference.child("GioHang").child(user.getUid()).child(cartList.get(position).getId()+"").child("number").setValue(cartList.get(position).getNumber());
                notifyDataSetChanged();
                CartFragment.tongtien();
                if(sl<=0){
                    AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).setTitle("Bạn có chắn chắn xóa?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    databaseReference.child("GioHang").child(user.getUid()).child(cartList.get(position).getId()+"").removeValue();
                                    MainActivity.listCart.remove(cartList.get(position));
                                    notifyDataSetChanged();
                                    CartFragment.tongtien();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sl++;
                                    cartList.get(position).setNumber(sl);
                                    MainActivity.TONG_TIEN+=cartList.get(position).getMoney();
                                    databaseReference.child("GioHang").child(user.getUid()).child(cartList.get(position).getId()+"").child("number").setValue(1);
                                    notifyDataSetChanged();
                                    CartFragment.tongtien();
                                }
                            }).create();
                    alertDialog.show();
                }
                tvNumberItem.setText(sl+"");
            }
        });
        imgTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl = cartList.get(position).getNumber();
                sl++;
                cartList.get(position).setNumber(sl);
                tvNumberItem.setText(sl+"");
                databaseReference.child("GioHang").child(user.getUid()).child(cartList.get(position).getId()+"").child("number").setValue(cartList.get(position).getNumber());
                notifyDataSetChanged();
                MainActivity.TONG_TIEN+=cartList.get(position).getMoney();
                CartFragment.tongtien();
            }
        });
        return view;
    }
}
