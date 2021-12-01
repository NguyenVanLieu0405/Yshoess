package com.example.nvl.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.nvl.Adapter.AdapterDonHang;
import com.example.nvl.Adapter.AdapterDonHangNhan;
import com.example.nvl.MainActivity;
import com.example.nvl.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DonHangNhanFragment extends Fragment {
    ListView listView;
    AdapterDonHangNhan adapterDonHang;

    public static DonHangNhanFragment newInstance() {

        Bundle args = new Bundle();

        DonHangNhanFragment fragment = new DonHangNhanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_donhangnhan,container,false);
        listView=view.findViewById(R.id.lvDonhangnhan);
        adapterDonHang = new AdapterDonHangNhan(MainActivity.donHangDatList,getContext());
        listView.setAdapter(adapterDonHang);
        Button btn = view.findViewById(R.id.btnXoahetDon);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.donHangDatList.size()>0){
                    AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).setTitle("Xóa hết thông tin các đơn đã nhận?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    if (user != null) {

                                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                                        databaseReference.child("DonHangDat").child(user.getUid()).removeValue();
                                        MainActivity.donHangDatList.clear();
                                        adapterDonHang.notifyDataSetChanged();
                                    }
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).create();
                    alertDialog.show();
                }

            }
        });
        return view;
    }
}
