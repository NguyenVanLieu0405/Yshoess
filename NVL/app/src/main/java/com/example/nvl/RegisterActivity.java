package com.example.nvl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.nvl.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        listener();
        progressDialog=new ProgressDialog(this);
    }

    private void listener() {
        binding.btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etsdt.getText().toString().equals("")){
                    binding.etsdt.setError("Không được để trống!");
                    return;
                }

                if (binding.etmk.getText().toString().equals("")){
                    binding.etmk.setError("Không được để trống!");
                    return;
                }

                if (binding.etmkconfirm.getText().toString().equals("")){
                    binding.etmkconfirm.setError("Không được để trống!");
                    return;
                }

                if (!binding.etmk.getText().toString().trim().equals(binding.etmkconfirm.getText().toString().trim())){
                    binding.etmkconfirm.setError("Mật khẩu không khớp!");
                    return;
                }
                onClickbtn();
            }
        });
        binding.layoutquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickbtn() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.createUserWithEmailAndPassword(binding.etsdt.getText().toString().trim(), binding.etmk.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(),MainActivity.class);
                            startActivity(intent);
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            firebaseDatabase=FirebaseDatabase.getInstance();
                            databaseReference=firebaseDatabase.getReference();
                            databaseReference.child("GioHang").child(user.getUid()).setValue("id_giohang");
                            databaseReference.child("DonHang").child(user.getUid()).setValue("id_donhang");
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}