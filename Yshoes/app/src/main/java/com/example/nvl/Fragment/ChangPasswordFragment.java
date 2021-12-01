package com.example.nvl.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nvl.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangPasswordFragment extends Fragment {
     EditText etPass,etNewPass,etConfirmPass;
     Button btnOK;
     ProgressDialog progressDialog;
    public static ChangPasswordFragment newInstance() {

        Bundle args = new Bundle();

        ChangPasswordFragment fragment = new ChangPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password,container,false);
//        etPass=view.findViewById(R.id.etOldPass);
        etNewPass=view.findViewById(R.id.etNewPass);
        etConfirmPass=view.findViewById(R.id.etConfirmPass);
        btnOK=view.findViewById(R.id.btnChangePass);
        progressDialog=new ProgressDialog(getContext());
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNewPass.getText().toString().trim().equals(etConfirmPass.getText().toString().trim())){
                    etConfirmPass.setError("Mật khẩu không khớp");
                    return;
                }
                if(etNewPass.getText().toString().equals("")){
                    etNewPass.setError("Mật khẩu không được để trống");
                    return;
                }if(etConfirmPass.getText().toString().equals("")){
                    etConfirmPass.setError("Mật khẩu không được để trống");
                    return;
                }
                onClickOK();
            }
        });
        return view;
    }

    private void onClickOK() {
        String pass = etNewPass.getText().toString().trim();
        progressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = pass;
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),"Đổi mật khẩu thành công!",Toast.LENGTH_LONG).show();
                        } else  {
                            Toast.makeText(getActivity(),"Đổi mật khẩu thất bại!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
