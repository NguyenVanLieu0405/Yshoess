package com.example.nvl.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.nvl.Class.User;
import com.example.nvl.Interface.ISendData;
import com.example.nvl.MainActivity;
import com.example.nvl.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import static com.example.nvl.MainActivity.MY_REQUEST_CODE;


public class UserFragment extends Fragment {
    public static final String TAG = UserFragment.class.getName();
    MainActivity mainActivity;
    Context context;
    ISendData iSendData;
    ImageView img,imgedit;
    EditText etUser,etEmail;
    Button btn;
    private  Uri uri;
    private ProgressDialog progressDialog;
    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public static UserFragment newInstance() {

        Bundle args = new Bundle();

        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        mainActivity=(MainActivity)getActivity();
        etUser=view.findViewById(R.id.etUserFrag);
        etEmail=view.findViewById(R.id.etEmailFrag);
        img=view.findViewById(R.id.imgUserFrag);
        btn=view.findViewById(R.id.btnCapnhat);
        progressDialog=new ProgressDialog(getActivity());
        setUserInfor();
        listener();
        return view;
    }

    private void listener() {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdate();
            }
        });
    }

    private void onClickUpdate() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        progressDialog.show();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(etUser.getText().toString().trim())
                .setPhotoUri(uri)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),"Cập nhật thành công",Toast.LENGTH_LONG).show();
                            mainActivity.showUser();
                        }
                    }
                });
    }

    private void setUserInfor() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        etUser.setText(user.getDisplayName());
        etEmail.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoUrl()).error(R.drawable.avt).into(img);
    }

    private void onClickRequestPermission() {
        if(mainActivity==null){
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mainActivity.openGalerry();
            return;
        }
        if (getActivity().checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mainActivity.openGalerry();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permission,MY_REQUEST_CODE);
        }
    }
    public void setBitmap(Bitmap bitmap){
        img.setImageBitmap(bitmap);
    }


}
