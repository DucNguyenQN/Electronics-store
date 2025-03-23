package com.example.appadmin;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appadmin.adapter.DonHangAdapter;
import com.example.appadmin.api.API;
import com.example.appadmin.databinding.ActivityDonHangBinding;
import com.example.appadmin.model.DataResponse;
import com.example.appadmin.model.HistoryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonHangActivity extends AppCompatActivity {
    private ActivityDonHangBinding binding;
    private DonHangAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDonHangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        adapter = new DonHangAdapter(this);
        binding.recycleDonHang.setLayoutManager(new LinearLayoutManager(this));
        getOrder();

    }

    private void getOrder() {
        API.apiService.xemdonhang(0).enqueue(new Callback<DataResponse<List<HistoryResponse>>>() {
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
}