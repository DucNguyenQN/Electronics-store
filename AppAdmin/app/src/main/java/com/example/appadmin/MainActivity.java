package com.example.appadmin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appadmin.api.API;
import com.example.appadmin.databinding.ActivityMainBinding;
import com.example.appadmin.model.DataResponse;
import com.example.appadmin.model.ThongKe;
import com.example.appadmin.ulttil.Ultils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //getToken();
        binding.themsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ThemSanPhamActivity.class));
            }
        });
        binding.suasanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SuaSanPhamActivity.class));
            }
        });
        binding.dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, DangNhapActivity.class));
                finish();
            }
        });
        binding.donhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DonHangActivity.class));
            }
        });
        binding.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });
        binding.thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ThongKeActivity.class));
            }
        });
    }
//    private void getToken(){
//        FirebaseMessaging.getInstance().getToken()
//                .addOnSuccessListener(new OnSuccessListener<String>() {
//                    @Override
//                    public void onSuccess(String s) {
//                        if (!TextUtils.isEmpty(s)){
//                                API.apiService.updateToken(Ultils.user_current.getId(), s).enqueue(new Callback<DataResponse>() {
//                                    @Override
//                                    public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {}
//
//                                    @Override
//                                    public void onFailure(Call<DataResponse> call, Throwable t) {}
//                                });
//                        }
//                    }
//                });
//    }
}