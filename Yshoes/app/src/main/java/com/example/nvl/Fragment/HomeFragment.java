package com.example.nvl.Fragment;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nvl.Adapter.AdapterRecyclerView;
import com.example.nvl.Class.Product;
import com.example.nvl.Class.Sort_Product;
import com.example.nvl.Interface.ISendData;
import com.example.nvl.Interface.ItemClickListener;
import com.example.nvl.MainActivity;
import com.example.nvl.R;
import com.example.nvl.Sql.SqlHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HomeFragment extends Fragment {
    public  static final String TAG = HomeFragment.class.getName();
    ImageView imgNike, imgAdidas, imgVans, imgFashion,imgSearch;
    AdapterRecyclerView adapterRecyclerViewProduct;
    List<Product> products, productsNike, productAdidas, productVans, productFashion;
    EditText etSearch;
    RecyclerView rvProduct;
    ISendData iSendData;
    TextView tvXemtatca;
    MainActivity mMainActivity;
    TextView tvProduct_kind;
    SqlHelper helper;
    ViewFlipper vf;
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iSendData=(ISendData)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mMainActivity = (MainActivity) getActivity();
        imgNike = view.findViewById(R.id.imgNike);
        imgAdidas = view.findViewById(R.id.imgAdidas);
        imgVans = view.findViewById(R.id.imgVans);
        imgFashion = view.findViewById(R.id.imgFashion);
        imgSearch=view.findViewById(R.id.imgSearch);
        etSearch=view.findViewById(R.id.etSearch);
        tvXemtatca=view.findViewById(R.id.tvXemtatca);

        vf=view.findViewById(R.id.vfhome);
        actionViewFlipper();

        helper=new SqlHelper(getContext());
        products = new ArrayList<>();
        productsNike = new ArrayList<>();
        productAdidas = new ArrayList<>();
        productFashion = new ArrayList<>();
        productVans = new ArrayList<>();

        tvProduct_kind=view.findViewById(R.id.tvProduct_kind);
        rvProduct = view.findViewById(R.id.rvNewproduct);
        RecyclerView.LayoutManager layoutNew = new GridLayoutManager(getContext(),2,
                RecyclerView.VERTICAL,
                false);
        rvProduct.setLayoutManager(layoutNew);
        products=helper.onGetProduct();
        productsNike = Sort_Product.NikeProduct(products);
        productAdidas = Sort_Product.AdidasProduct(products);
        productVans = Sort_Product.VansProduct(products);
        productFashion = Sort_Product.FashionProduct(products);

        Comparator<Product> crate = new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {

                return (int) (o2.getRate() -o1.getRate()+0.5);
            }
        };

        adapterRecyclerViewProduct = new AdapterRecyclerView(productsNike, getContext(), new ItemClickListener() {
            @Override
            public void onClick(Product product) {
                mMainActivity.sendItem(product);
            }
        });
        rvProduct.setAdapter(adapterRecyclerViewProduct);
        imgNike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvProduct_kind.setText("Giày Nike");
                adapterRecyclerViewProduct = new AdapterRecyclerView(productsNike, getContext(), new ItemClickListener() {
                    @Override
                    public void onClick(Product product) {
                        mMainActivity.sendItem(product);
                    }
                });
                rvProduct.setAdapter(adapterRecyclerViewProduct);
            }
        });
        imgAdidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvProduct_kind.setText("Giày Adidas");
                adapterRecyclerViewProduct = new AdapterRecyclerView(productAdidas, getContext(), new ItemClickListener() {
                    @Override
                    public void onClick(Product product) {
                        mMainActivity.sendItem(product);
                    }
                });
                rvProduct.setAdapter(adapterRecyclerViewProduct);
            }
        });
        imgVans.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tvProduct_kind.setText("Giày Vans");
                adapterRecyclerViewProduct = new AdapterRecyclerView(productVans, getContext(), new ItemClickListener() {
                    @Override
                    public void onClick(Product product) {
                        mMainActivity.sendItem(product);
                    }
                });
                rvProduct.setAdapter(adapterRecyclerViewProduct);
            }
        });
        imgFashion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tvProduct_kind.setText("Giày thời trang");
                adapterRecyclerViewProduct = new AdapterRecyclerView(productFashion, getContext(), new ItemClickListener() {
                    @Override
                    public void onClick(Product product) {
                        mMainActivity.sendItem(product);
                        rvProduct.setAdapter(adapterRecyclerViewProduct);
                    }
                });
                rvProduct.setAdapter(adapterRecyclerViewProduct);
            }
        });
        tvXemtatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(products,crate);
                sendData(products);
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) mMainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
                mMainActivity.sendFind(etSearch.getText().toString().trim());
            }
        });
        return view;
    }

    private void sendData(List<Product> products) {
        iSendData.sendData(products);
    }
    private void actionViewFlipper(){
        vf.setFlipInterval(1000);
        vf.setAutoStart(true);
    }
}



