package com.example.nvl;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nvl.Class.DonHang;
import com.example.nvl.Class.Product;
import com.example.nvl.Class.ProductCart;
import com.example.nvl.Class.User;
import com.example.nvl.Fragment.CartFragment;
import com.example.nvl.Fragment.ChangPasswordFragment;
import com.example.nvl.Fragment.DonHangFragment;
import com.example.nvl.Fragment.DonHangNhanFragment;
import com.example.nvl.Fragment.FindFragment;
import com.example.nvl.Fragment.HomeFragment;
import com.example.nvl.Fragment.ItemFragment;
import com.example.nvl.Fragment.ListFragment;
import com.example.nvl.Fragment.UserFragment;
import com.example.nvl.Interface.ISendData;
import com.example.nvl.Sql.SqlHelper;
import com.example.nvl.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements ISendData, NavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding binding;
    public static int SL = 0;
    public static int TONG_TIEN = 0;
    public static int TIEN = 0;
    public static int ID = 0;
    public static int ID_DAT = 0;
    public static final int MY_REQUEST_CODE = 10;

    public static List<ProductCart> listCart = new ArrayList<>();
    public static List<Product> productList = new ArrayList<>();
    public static List<DonHang> donHangList = new ArrayList<>();
    public static List<DonHang> donHangDatList = new ArrayList<>();
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_CART = 1;
    private static final int FRAGMENT_PROFILE = 2;
    private static final int FRAGMENT_PASSWORD = 3;
    private static final int FRAGMENT_ITEM = 4;
    private static final int FRAGMENT_DONHANG = 5;
    private static final int FRAGMENT_DONHANGDANHAN = 6;
    final private UserFragment userFragment = new UserFragment();
    private int current = FRAGMENT_HOME;
    SqlHelper helper;
    String urlStr = "https://demo0193625.mockable.io/products";
    TextView tvUser, tvEmail;
    ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerlayout, binding.toolbar, R.string.nav_open, R.string.nav_close);
        binding.drawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navigationView.setNavigationItemSelectedListener(this);
        helper = new SqlHelper(getBaseContext());
        View header = binding.navigationView.getHeaderView(0);
        tvEmail = header.findViewById(R.id.tvEmail);
        tvUser = header.findViewById(R.id.tvUser);
        avatar = header.findViewById(R.id.avatar);
        new DataGetProduct().execute();
//        getAPI();
        getListCart();
        getDonHangList();
        getDonHangDatList();
        replace(new HomeFragment());
        binding.navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        showUser();

    }

    public void showUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            binding.navigationView.getMenu().findItem(R.id.nav_logout).setTitle("Đăng nhập");
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photo = user.getPhotoUrl();
        if (name == null) {
            tvUser.setVisibility(View.GONE);
        } else {
            tvUser.setVisibility(View.VISIBLE);
            tvUser.setText(name);
        }
        tvEmail.setText(email);
        if (photo != null)
            Glide.with(this).load(photo).error(R.drawable.avt).into(avatar);

    }

    @Override
    public void sendData(List<Product> list) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list_item", (Serializable) list);
        listFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.idControl, listFragment);
        fragmentTransaction.addToBackStack(HomeFragment.TAG);
        fragmentTransaction.commit();
        current = FRAGMENT_ITEM;
    }

    @Override
    public void sendDataCart(List<ProductCart> list) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        CartFragment cartFragment = new CartFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list_item_cart", (Serializable) list);
        cartFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.idControl, cartFragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(HomeFragment.TAG);
        current = FRAGMENT_CART;
    }

    class DataGetProduct extends AsyncTask<Void, Void, String> {
        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder s = new StringBuilder();
            try {
                URL url = new URL(urlStr);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();

                int byteChar;
                BufferedReader rd = new BufferedReader
                        (new InputStreamReader(is, Charset.forName("UTF-8")));

                while ((byteChar = rd.read()) != -1) {
                    s.append((char) byteChar);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            result = s.toString();
            return result;
        }

        @Override
        protected void onPostExecute(String unused) {
            super.onPostExecute(unused);

            try {
//                productList.clear();
                JSONArray array = new JSONArray(result);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String name = jsonObject.getString("name");
                    String review = jsonObject.getString("review");
                    int money = jsonObject.getInt("money");
                    String kind = jsonObject.getString("kind");
                    String img = jsonObject.getString("img");
                    Double rate = jsonObject.getDouble("rate");
                    String size = jsonObject.getString("size");
                    String color = jsonObject.getString("color");
                    int number = jsonObject.getInt("number");
//                    productList.add(new Product(id, name, review, money, kind, img, rate, size, color, number));
                    helper.onAddProduct(id, name, review, money, kind, img, rate, size, color, number);
                }
//                Toast.makeText(getBaseContext(),productList.toString(),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            if (current != FRAGMENT_HOME) {
                replace(new HomeFragment());
                current = FRAGMENT_HOME;
            }
        } else if (id == R.id.nav_cart) {
            if (current != FRAGMENT_CART) {
                replace(new CartFragment());
                current = FRAGMENT_CART;
            }
        } else if (id == R.id.nav_account) {
            if (current != FRAGMENT_PROFILE) {
                replace(userFragment);
                current = FRAGMENT_PROFILE;
            }
        } else if (id == R.id.nav_pass) {
            if (current != FRAGMENT_PASSWORD) {
                replace(new ChangPasswordFragment());
                current = FRAGMENT_PASSWORD;
            }
        } else if (id == R.id.nav_donhang) {
            if (current != FRAGMENT_DONHANG) {
                replace(new DonHangFragment());
                current = FRAGMENT_DONHANG;
            }
        } else if (id == R.id.nav_donhangdanhan) {
            if (current != FRAGMENT_DONHANGDANHAN) {
                replace(new DonHangNhanFragment());
                current = FRAGMENT_DONHANGDANHAN;
            }
        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        binding.drawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerlayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerlayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.idControl, fragment);
        transaction.commit();
    }

    public void sendItem(Product product) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ItemFragment itemFragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        itemFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.idControl, itemFragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(HomeFragment.TAG);
        current = FRAGMENT_ITEM;
    }

    public void sendFind(String find) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        FindFragment findFragment = new FindFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("find", find);
        findFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.idControl, findFragment);
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(HomeFragment.TAG);
        current = FRAGMENT_ITEM;
    }

    final private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent == null) return;
                        Uri uri = intent.getData();
                        userFragment.setUri(uri);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            userFragment.setBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGalerry();
            }
        }

    }

    public void openGalerry() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    private List<ProductCart> getListCart() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("GioHang").child(user.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listCart.clear();
                    TONG_TIEN = 0;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        ProductCart cart = data.getValue(ProductCart.class);
                        TONG_TIEN += cart.getMoney() * cart.getNumber();
                        listCart.add(cart);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        } else listCart.clear();
        return listCart;
    }

    private List<DonHang> getDonHangList() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("DonHang").child(user.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    donHangList.clear();
                    MainActivity.ID = 0;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        DonHang donHang = data.getValue(DonHang.class);
                        ID++;
                        donHangList.add(donHang);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        } else donHangList.clear();
        return donHangList;
    }

    private List<DonHang> getDonHangDatList() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("DonHangDat").child(user.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    donHangDatList.clear();
                    MainActivity.ID_DAT = 0;
                    for (DataSnapshot data : snapshot.getChildren()) {
                        DonHang donHang = data.getValue(DonHang.class);
                        ID_DAT++;
                        donHangDatList.add(donHang);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        } else donHangDatList.clear();
        return donHangDatList;
    }


}