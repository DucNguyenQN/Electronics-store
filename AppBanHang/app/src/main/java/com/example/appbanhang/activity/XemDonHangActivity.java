package com.example.appbanhang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.DonHangAdapter;
import com.example.appbanhang.databinding.ActivityXemDonHangBinding;
import com.example.appbanhang.model.DataResponse;
import com.example.appbanhang.model.HistoryResponse;
import com.example.appbanhang.retrofit.API;
import com.example.appbanhang.ulttil.Ultils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XemDonHangActivity extends AppCompatActivity {
    private ActivityXemDonHangBinding binding;
    private DonHangAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityXemDonHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        adapter = new DonHangAdapter(this);
        binding.recycleDonHang.setLayoutManager(new LinearLayoutManager(this));
        innitToolbar();
        getOrder();

    }

    private void getOrder() {
        API.apiService.xemdonhang(Ultils.user_current.getId()).enqueue(new Callback<DataResponse<List<HistoryResponse>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<HistoryResponse>>> call, Response<DataResponse<List<HistoryResponse>>> response) {
                if (response.body() != null){
                    List<HistoryResponse> historyResponse = response.body().getResult();
                    adapter.setData(historyResponse);
                    binding.recycleDonHang.setAdapter(adapter);
                    //Toast.makeText(XemDonHangActivity.this, "Thanh cng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<HistoryResponse>>> call, Throwable t) {
            }
        });
    }

    private void innitToolbar() {
        setSupportActionBar(binding.toolbarDonHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarDonHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}