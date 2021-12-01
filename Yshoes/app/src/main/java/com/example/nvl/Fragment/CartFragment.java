package com.example.nvl.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nvl.Adapter.AdapterCart;
import com.example.nvl.Class.ProductCart;
import com.example.nvl.DatHangActivity;
import com.example.nvl.Interface.ISendData;
import com.example.nvl.MainActivity;
import com.example.nvl.R;

import java.util.List;

public class CartFragment extends Fragment {
    Context context;
    ListView listView;
    MainActivity mMainActivity;
    AdapterCart adapterCart;
    ISendData iSendData;
    Button btnDatHang;
    static TextView tvTongTien;
    public static CartFragment newInstance() {

        Bundle args = new Bundle();
        TextView tvTongTien;
        CartFragment fragment = new CartFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        iSendData=(ISendData)getActivity();
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart,container,false);
        btnDatHang=view.findViewById(R.id.btnDatHang);
        mMainActivity=(MainActivity) getActivity();
        Bundle receiveBundle = getArguments();
//        if(receiveBundle!=null){
//            List<ProductCart> products = (List<ProductCart>) receiveBundle.get("list_item_cart");
            listView = view.findViewById(R.id.lvCart);
            adapterCart = new AdapterCart(MainActivity.listCart, getContext());
            listView.setAdapter(adapterCart);
            tvTongTien = view.findViewById(R.id.tvTongTienCart);
            tongtien();
//        }
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.listCart.size()==0){
                    Toast.makeText(getContext(),"Giỏ hàng rỗng",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(getActivity(), DatHangActivity.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }
    public static void tongtien(){
        tvTongTien.setText("Tổng tiền : "+MainActivity.TONG_TIEN+" đồng");

    }
}
