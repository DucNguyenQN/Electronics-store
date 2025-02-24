package com.example.appbanhang.activity;

import static com.example.appbanhang.activity.MainActivity.manggiohang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.SanPham;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPham extends AppCompatActivity {
    Toolbar toolbarchitiet;
    ImageView imgchitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinner;
    Button btndatmua;
    NotificationBadge notificationBadge;
    FrameLayout cart;

    int id=0;
    Double giachitiet = (double) 0;
    String tenchitiet="";
    //int giachitiet = 0;
    String hinhanhchitiet = "";
    //String motachitiet="";
    //int idsanpham = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        ActionToolBar();
        GetInfomation();
        CatchEventSpinner();
        EventButton();
    }

    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(manggiohang.size() > 0){
                    boolean flag = false;
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    for(int i = 0; i< manggiohang.size(); i++){
                        if (manggiohang.get(i).getId() == id){
                            manggiohang.get(i).setSoluongsp(sl + manggiohang.get(i).getSoluongsp());
                            double gia = (giachitiet * manggiohang.get(i).getSoluongsp());
                            manggiohang.get(i).setGiasp(gia);
                            flag = true;
                        }
                    };
                    if (flag == false){
                        double gia = sl * giachitiet;
                        manggiohang.add(new GioHang(id, tenchitiet, gia, hinhanhchitiet, sl));
                    }
                }else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    double GiaMoi = soluong * giachitiet;
                    manggiohang.add(new GioHang(id, tenchitiet, GiaMoi, hinhanhchitiet, soluong));
                }
                notificationBadge.setText(String.valueOf(manggiohang.size()));
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInfomation() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanPham.getId();
        tenchitiet = sanPham.getTensanpham();
        hinhanhchitiet = sanPham.getHinhanh();
        txtten.setText(tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giachitiet = Double.parseDouble(sanPham.getGia());
        txtgia.setText("Giá: "+decimalFormat.format(giachitiet)+"VND");
        txtmota.setText(sanPham.getMota());
        Glide.with(getApplicationContext()).load(hinhanhchitiet).into(imgchitiet);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        cart = findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChiTietSanPham.this, GioHangActivity.class));
            }
        });
        toolbarchitiet = findViewById(R.id.toolbarChitietsp);
        imgchitiet = findViewById(R.id.imgchitietsp);
        txtten = findViewById(R.id.txttenchitietsp);
        txtgia = findViewById(R.id.txtgiachitietsp);
        txtmota = findViewById(R.id.txtmotachitietsp);
        spinner = findViewById(R.id.spinner);
        btndatmua = findViewById(R.id.btndatmua);
        notificationBadge = findViewById(R.id.menu_sl);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (manggiohang == null){
            manggiohang = new ArrayList<>();
        }else {
            notificationBadge.setText(String.valueOf(manggiohang.size()));
        }
    }
}