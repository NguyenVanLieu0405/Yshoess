package com.example.nvl.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nvl.Adapter.AdapterDonHang;
import com.example.nvl.MainActivity;
import com.example.nvl.R;

public class DonHangFragment extends Fragment {
    ListView listView;
    AdapterDonHang adapterDonHang;
    public static DonHangFragment newInstance() {

        Bundle args = new Bundle();

        DonHangFragment fragment = new DonHangFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_donhang,container,false);
        listView=view.findViewById(R.id.lvDonhang);

        adapterDonHang = new AdapterDonHang(MainActivity.donHangList);
        listView.setAdapter(adapterDonHang);
        return view;

    }
}
