package com.example.appbanhang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.SanPhamAdapter;
import com.example.appbanhang.adapter.SearchAdapter;
import com.example.appbanhang.databinding.ActivitySearchBinding;
import com.example.appbanhang.model.DataResponse;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.retrofit.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding binding;
    SearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        adapter = new SearchAdapter(this);
        binding.recycleSearch.setLayoutManager(new LinearLayoutManager(this));
        ActionToolBar();
        getData();
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filter(s);
                return false;
            }
        });
    }

    private void getData() {
        API.apiService.getSanPham().enqueue(new Callback<DataResponse<List<SanPham>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<SanPham>>> call, retrofit2.Response<DataResponse<List<SanPham>>> response) {
                if (response.body().getResult() != null){
                    List<SanPham> sanpham = response.body().getResult();
                    adapter.setData(sanpham);
                    binding.recycleSearch.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<SanPham>>> call, Throwable t) {

            }
        });
    }

    private void ActionToolBar() {
        setSupportActionBar(binding.toolbarSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbarSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}