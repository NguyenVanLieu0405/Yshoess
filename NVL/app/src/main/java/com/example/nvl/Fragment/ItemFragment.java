package com.example.nvl.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.nvl.Adapter.AdapterComment;
import com.example.nvl.Class.Comment;
import com.example.nvl.Class.DonHang;
import com.example.nvl.Class.Product;
import com.example.nvl.Class.ProductCart;
import com.example.nvl.Interface.ISendData;
import com.example.nvl.LoginActivity;
import com.example.nvl.MainActivity;
import com.example.nvl.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment {
    Context context;
    MainActivity mMain;
    ISendData iSendData;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String mau, size;
    public static List<Comment> comments = new ArrayList<>();
    ListView listView;
    public static int ID_COMMENT = 0;
    Product product;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iSendData = (ISendData) getActivity();
    }

    public static ItemFragment newInstance() {

        Bundle args = new Bundle();

        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        Bundle receiveBundle = getArguments();
        mMain = (MainActivity) getActivity();
        MainActivity.SL = 0;
        MainActivity.TIEN = 0;
        product = (Product) receiveBundle.get("product");
        listView = view.findViewById(R.id.lvComment);
        getCommentList();
        AdapterComment adapterComment = new AdapterComment(comments, getContext());
        listView.setAdapter(adapterComment);
        ImageView img = view.findViewById(R.id.imgItemView);
        RatingBar rt = view.findViewById(R.id.ratingItem);
        TextView tvName = view.findViewById(R.id.tvItemName);
        TextView tvMoney = view.findViewById(R.id.tvItemMoney);
        TextView tvReview = view.findViewById(R.id.tvItemReview);
        TextView etNumber = view.findViewById(R.id.etItemNumber);
//        TextView tvTongtien = view.findViewById(R.id.tvTongtien);
        Spinner spinnerColor = view.findViewById(R.id.spnColor);
        Spinner spinnerSize = view.findViewById(R.id.spnSize);
        ImageView imgGiam = view.findViewById(R.id.imgGiam);
        ImageView imgTang = view.findViewById(R.id.imgTang);
        RelativeLayout imgAddCart = view.findViewById(R.id.imgAddCart);
        RelativeLayout imgCart = view.findViewById(R.id.imgCart);
        ImageView imgPost = view.findViewById(R.id.imgPost);
        EditText etCmt = view.findViewById(R.id.etComment);

        imgGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.SL > 0) {
                    MainActivity.SL--;
                    MainActivity.TIEN -= product.getMoney();
                    etNumber.setText(MainActivity.SL + "");
//                    tvTongtien.setText(MainActivity.TIEN + "");
                }
            }
        });
        imgTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.SL++;
                MainActivity.TIEN += product.getMoney();
                etNumber.setText(MainActivity.SL + "");
//                tvTongtien.setText(MainActivity.TIEN + "");
            }
        });

        Picasso.with(context).load(product.getImg()).into(img);
        rt.setRating((float) product.getRate());
        tvName.setText(product.getName());
        tvMoney.setText(product.getMoney() + "");
        tvReview.setText(product.getReview());

        String s = product.getColor();
        String[] spn = s.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < spn.length; i++) {
            list.add(spn[i]);
        }
        ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerColor.setAdapter(adapter);
        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mau = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String s2 = product.getSize();
        String[] spn2 = s2.split(",");
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < spn2.length; i++) {
            list2.add(spn2[i]);
        }
        ArrayAdapter adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list2);
        adapter2.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerSize.setAdapter(adapter2);
        spinnerSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                size = list2.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        imgAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.SL > 0) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user == null) {
                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                .setTitle("Bạn chưa đăng nhập vui lòng đăng nhập!")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }).create();
                        alertDialog.show();
                    } else {
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference = firebaseDatabase.getReference();
                        boolean flag = false;
                        for (ProductCart cart : MainActivity.listCart) {
                            if (cart.getId() == product.getId()) {
                                cart.setNumber(cart.getNumber() + MainActivity.SL);
                                databaseReference.child("GioHang").child(user.getUid()).child(cart.getId() + "").setValue(cart);
                                flag = true;
                            }
                        }
                        if (flag == false) {
                            ProductCart productCart = new ProductCart(product.getId(), product.getName(), product.getReview(), product.getMoney(), product.getKind(), product.getImg(), product.getRate(), mau, size, MainActivity.SL);
                            MainActivity.listCart.add(productCart);
                            databaseReference.child("GioHang").child(user.getUid()).child(productCart.getId() + "").setValue(productCart);
                        }

                        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                                .setTitle("Đã thêm vào giỏ hàng")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).create();
                        alertDialog.show();
                    }
                }

            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCart(MainActivity.listCart);
            }
        });

        imgPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etCmt.getText().toString().trim().equals("")) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user!=null){
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        ID_COMMENT++;
                        Comment comment = new Comment(ID_COMMENT, user.getDisplayName(), etCmt.getText().toString());
                        databaseReference.child("Comment").child(product.getId() + "").child(ID_COMMENT + "").setValue(comment);
                        etCmt.setText("");
                        InputMethodManager inputMethodManager = (InputMethodManager) mMain.getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        Toast.makeText(getContext(),"Đã comment",Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getContext(),"Chưa đăng nhập, không thể comment",Toast.LENGTH_LONG).show();

                }
            }
        });

        return view;
    }

    private void showCart(List<ProductCart> listCart) {
        iSendData.sendDataCart(listCart);
    }

    public List<Comment> getCommentList() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("Comment").child(product.getId() + "");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    comments.clear();
                    ID_COMMENT = 0;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Comment comment = data.getValue(Comment.class);
                        ID_COMMENT++;
                        comments.add(comment);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }
        else comments.clear();
        return comments;
    }
}
