package com.example.nvl.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nvl.Adapter.AdapterProduct;
import com.example.nvl.Class.Product;
import com.example.nvl.Interface.ItemClickListener;
import com.example.nvl.MainActivity;
import com.example.nvl.R;
import com.example.nvl.Sql.SqlHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FindFragment extends Fragment {
    MainActivity mMainActivity;
    ListView listView;
    AdapterProduct adapterProduct;
    TextView tvSearch;
    SqlHelper helper;
    public static FindFragment newInstance() {

        Bundle args = new Bundle();

        FindFragment fragment = new FindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find,container,false);
        Bundle receiveBundle = getArguments();
        mMainActivity=(MainActivity) getActivity();
        tvSearch=view.findViewById(R.id.tvSearch);
        listView = view.findViewById(R.id.lvSearch);
        helper=new SqlHelper(getContext());
        Bundle s = getArguments();
        String tv = (String) s.get("find");
        tvSearch.setText(tv);
        List<Product> products = new ArrayList<>();
        List<Product> product = new ArrayList<>();
        products=helper.onGetProduct();
        for(int i=0;i<products.size();i++){
            if(products.get(i).getName().toLowerCase(Locale.ROOT).contains(tvSearch.getText().toString().toLowerCase(Locale.ROOT))||products.get(i).getReview().toLowerCase(Locale.ROOT).contains(tvSearch.getText().toString().toLowerCase(Locale.ROOT))){
                product.add(products.get(i));
            }
        }
        adapterProduct = new AdapterProduct(product, getContext(), new ItemClickListener() {
            @Override
            public void onClick(Product product) {
                mMainActivity.sendItem(product);
            }
        });
        listView.setAdapter(adapterProduct);
        return view;
    }

}
