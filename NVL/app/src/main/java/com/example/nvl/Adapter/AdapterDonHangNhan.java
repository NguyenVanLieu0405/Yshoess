package com.example.nvl.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nvl.Class.DonHang;
import com.example.nvl.MainActivity;
import com.example.nvl.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AdapterDonHangNhan extends BaseAdapter {
    List<DonHang> donHangList;
    Context context;

    public AdapterDonHangNhan(List<DonHang> donHangList, Context context) {
        this.donHangList = donHangList;
        this.context = context;
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
        View view = inflater.inflate(R.layout.iten_donhangnhan,parent,false);
        TextView tv = view.findViewById(R.id.tvDonhangnhan);
        TextView tvngay = view.findViewById(R.id.tvNgaynhan);
        TextView tvsdt = view.findViewById(R.id.tvSDTnhannhan);
        TextView tvdc = view.findViewById(R.id.tvDiachinhannhan);
        TextView tvstpt = view.findViewById(R.id.tvSotienphaitranhan);
        RecyclerView lv = view.findViewById(R.id.lvItemdonhangnhan);
        Button btnxoa = view.findViewById(R.id.btnXoa1don);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        lv.setLayoutManager(layoutManager);
        AdapterRecycleDH product = new AdapterRecycleDH(donHangList.get(position).getCartList(),context);
        lv.setAdapter(product);
        tvdc.setText("Địa chỉ nhận hàng: "+donHangList.get(position).getAddress());
        tvsdt.setText("Số điện thoại nhận hàng: "+donHangList.get(position).getPhone());
        tv.setText("Bạn đã nhận: ");
        tvngay.setText("Ngày nhận: "+donHangList.get(position).getDate());
        tvstpt.setText("Số tiền đã thanh toán: "+donHangList.get(position).getMoney()+" đồng");
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).setTitle("Xóa thông tin đơn hàng?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                                    databaseReference.child("DonHangDat").child(user.getUid()).child(donHangList.get(position).getId()+"").removeValue();
                                    MainActivity.donHangDatList.remove(donHangList.get(position));
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
        return view;
    }
}
