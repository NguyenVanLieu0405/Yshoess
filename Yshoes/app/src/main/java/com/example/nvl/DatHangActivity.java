package com.example.nvl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.nvl.Class.DonHang;
import com.example.nvl.Class.ProductCart;
import com.example.nvl.databinding.ActivityDatHangBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatHangActivity extends AppCompatActivity implements LocationListener {
    ActivityDatHangBinding binding;
    ProgressDialog progressDialog;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dat_hang);
        progressDialog = new ProgressDialog(this);
//        if (ContextCompat.checkSelfPermission(DatHangActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(DatHangActivity.this, new String[]{
//                    Manifest.permission.ACCESS_FINE_LOCATION
//            }, 100);
//        }
        binding.imgbackhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        binding.imgmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                getLocation();
                progressDialog.dismiss();
            }
        });
        binding.btnThemdon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.etPhone.getText().toString().trim().equals("")) {
                    binding.etPhone.setError("Không được để trống!");
                    return;
                } else if (binding.etPhone.getText().toString().trim().length() != 10) {
                    binding.etPhone.setError("Số điện thoại không đúng!");
                    return;
                } else if (binding.etDiachi.getText().toString().trim().equals("")) {
                    binding.etDiachi.setError("Không được để trống!");
                    return;
                } else {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user == null) {
                        Toast.makeText(getBaseContext(), "Bạn chưa đăng nhập!", Toast.LENGTH_LONG).show();
                    }
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference();
                    AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
                            .setTitle("Bạn có chắc chắn đặt hàng?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Date date = new Date();
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy | HH:mm:ss");
                                    String s = dateFormat.format(date);
                                    DonHang donHang = new DonHang(MainActivity.ID + 1, s, binding.etPhone.getText().toString(), binding.etDiachi.getText().toString(),MainActivity.listCart, MainActivity.TONG_TIEN);
                                    MainActivity.donHangList.add(donHang);
                                    databaseReference.child("DonHang").child(user.getUid()).child(donHang.getId() + "").setValue(donHang);
                                    databaseReference.child("GioHang").child(user.getUid()).removeValue();
                                    Toast.makeText(getApplicationContext(), "Đặt hàng thành công!", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                    startActivity(intent);
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
    }

    private void getLocation() {
        try {

            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1, DatHangActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(DatHangActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String add = addresses.get(0).getAddressLine(0);
            binding.etDiachi.setText(add);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}