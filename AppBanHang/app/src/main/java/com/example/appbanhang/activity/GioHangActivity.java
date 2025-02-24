package com.example.appbanhang.activity;

import static com.example.appbanhang.activity.MainActivity.manggiohang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.GioHangAdapter;
import com.example.appbanhang.model.EvenBus.TinhTong;
import com.example.appbanhang.model.GioHang;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangActivity extends AppCompatActivity {
    TextView txtTongTien, txtgiohangtrong;
    RecyclerView recyclerViewGioHang;
    Toolbar toolbar;
    Button btnmuahang;
    List<GioHang> lstgiohang;
    GioHangAdapter adapter;
    long tongtien = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gio_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InnitView();
        InnitControl();
        tinhTongTien();
    }



    private void InnitControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerViewGioHang.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewGioHang.setLayoutManager(layoutManager);

        if (manggiohang.size() == 0){
            txtgiohangtrong.setVisibility(View.VISIBLE);
        }else {
            adapter = new GioHangAdapter(this);
            adapter.setData(manggiohang);
            recyclerViewGioHang.setAdapter(adapter);
        }
        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GioHangActivity.this, ThanhToanActivity.class);
                intent.putExtra("tongtien", tongtien);
                startActivity(intent);
            }
        });
    }
    private void tinhTongTien() {
        for(int i=0;i<manggiohang.size();i++){
            tongtien += manggiohang.get(i).getGiasp() * manggiohang.get(i).getSoluongsp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongtien)+" VND");
    }
    private void InnitView() {
        txtTongTien = findViewById(R.id.txttongtien);
        txtgiohangtrong = findViewById(R.id.giohangtrong);
        toolbar = findViewById(R.id.toolbar);
        recyclerViewGioHang = findViewById(R.id.reycleviewgiohang);
        btnmuahang = findViewById(R.id.btnmuahang);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void tinhtien(TinhTong event){
        if (event != null){
            tinhTongTien();
        }
    };
}