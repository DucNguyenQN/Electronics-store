package com.example.appadmin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.appadmin.adapter.SuaSanPhamAdapter;
import com.example.appadmin.api.API;
import com.example.appadmin.databinding.ActivitySuaSanPhamBinding;
import com.example.appadmin.model.DataResponse;
import com.example.appadmin.model.SanPham;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SuaSanPhamActivity extends AppCompatActivity {
    private ActivitySuaSanPhamBinding binding;
    SuaSanPhamAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySuaSanPhamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        adapter = new SuaSanPhamAdapter(this);
        binding.recycle.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }
    private void getData() {
        API.apiService.getSanPham().enqueue(new Callback<DataResponse<List<SanPham>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<SanPham>>> call, retrofit2.Response<DataResponse<List<SanPham>>> response) {
                if (response.body().getResult() != null){
                    List<SanPham> sanpham = response.body().getResult();
                    adapter.setData(sanpham);
                    binding.recycle.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<SanPham>>> call, Throwable t) {

            }
        });
    }
}