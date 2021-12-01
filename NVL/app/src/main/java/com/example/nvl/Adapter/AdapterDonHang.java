package com.example.nvl.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nvl.Class.DonHang;
import com.example.nvl.Class.Product;
import com.example.nvl.Fragment.DonHangFragment;
import com.example.nvl.Interface.ItemClickListener;
import com.example.nvl.LoginActivity;
import com.example.nvl.MainActivity;
import com.example.nvl.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterDonHang extends BaseAdapter {
    List<DonHang> donHangList;
    Context context;
    public AdapterDonHang(List<DonHang> donHangList) {
        this.donHangList = donHangList;
    }

    @Override
    public int getCount() {
        return donHangList.size();
    }

    @Override
    public Object getItem(int position) {
        return donHangList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_donhang,parent,false);
        TextView tv = view.findViewById(R.id.tvDonhang);
        TextView tvngay = view.findViewById(R.id.tvNgaydat);
        TextView tvsdt = view.findViewById(R.id.tvSDTnhan);
        TextView tvdc = view.findViewById(R.id.tvDiachinhan);
        TextView tvstpt = view.findViewById(R.id.tvSotienphaitra);
        RecyclerView lv = view.findViewById(R.id.lvItemdonhang);
        Button btndanhan = view.findViewById(R.id.btnDanhan);
//        AdapterItemDonHang product = new AdapterItemDonHang(donHangList.get(position).getCartList(),context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        lv.setLayoutManager(layoutManager);
        AdapterRecycleDH product = new AdapterRecycleDH(donHangList.get(position).getCartList(),context);
        lv.setAdapter(product);
        tvdc.setText("Địa chỉ nhận hàng: "+donHangList.get(position).getAddress());
        tvsdt.setText("Số điện thoại nhận hàng: "+donHangList.get(position).getPhone());
        tv.setText("Bạn đã đặt: ");
        tvngay.setText("Ngày đặt: "+donHangList.get(position).getDate());
        tvstpt.setText("Số tiền phải trả: "+donHangList.get(position).getMoney()+" đồng");
        Button btnhuy=view.findViewById(R.id.btnHuydon);
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).setTitle("Bạn có chắc chắn hủy đơn?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                                    databaseReference.child("DonHang").child(user.getUid()).child(donHangList.get(position).getId() + "").removeValue();
                                    donHangList.remove(donHangList.get(position));
                                    notifyDataSetChanged();
                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                alertDialog.show();
            }

        });
        btndanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                    databaseReference.child("DonHang").child(user.getUid()).child(donHangList.get(position).getId() + "").removeValue();

                    Date date = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy | HH:mm:ss");
                    String s = dateFormat.format(date);
                    DonHang donHang = new DonHang();
                    int id  = MainActivity.ID_DAT+1;
                    donHang.setId(id);
                    donHang.setAddress(donHangList.get(position).getAddress());
                    donHang.setCartList(donHangList.get(position).getCartList());
                    donHang.setMoney(donHangList.get(position).getMoney());
                    donHang.setPhone(donHangList.get(position).getPhone());
                    donHang.setDate(s);
                    databaseReference.child("DonHangDat").child(user.getUid()).child(donHang.getId()+"").setValue(donHang);
                    donHangList.remove(donHangList.get(position));
                    notifyDataSetChanged();
                    Toast.makeText(v.getContext(),"Đã nhận hàng thành công!",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
