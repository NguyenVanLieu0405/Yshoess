package com.example.nvl.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nvl.Adapter.AdapterProduct;
import com.example.nvl.Class.Product;
import com.example.nvl.Interface.ISendData;
import com.example.nvl.Interface.ItemClickListener;
import com.example.nvl.MainActivity;
import com.example.nvl.R;

import java.util.List;

public class ListFragment extends Fragment {
    ListView listView;
    AdapterProduct adapterProduct;
    ISendData iSendData;
    MainActivity mMainActivity;
    public static ListFragment newInstance() {
        Bundle args = new Bundle();
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lvitem,container,false);
        Bundle receiveBundle = getArguments();
        mMainActivity=(MainActivity) getActivity();
        List<Product> products = (List<Product>) receiveBundle.get("list_item");
        listView = view.findViewById(R.id.lvProduct);
        adapterProduct = new AdapterProduct(products, getContext(), new ItemClickListener() {
            @Override
            public void onClick(Product product) {
                mMainActivity.sendItem(product);
            }
        });
        listView.setAdapter(adapterProduct);
        return view;
    }
}
