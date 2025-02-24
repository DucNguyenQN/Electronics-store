package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.LoaispAdapter;
import com.example.appbanhang.adapter.SanPhamAdapter;
import com.example.appbanhang.model.DataResponse;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.LoaiSanPham;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.retrofit.API;
import com.example.appbanhang.retrofit.Apibanhang;
import com.example.appbanhang.ulttil.CheckConnection;
import com.example.appbanhang.ulttil.Server;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView lstMain;
    List<LoaiSanPham> arrayLoaisp;
    LoaispAdapter loaispAdapter;

    SanPhamAdapter spAdapter;
    FrameLayout frameLayout;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Apibanhang apibanhang;
    NotificationBadge badge;
    public static ArrayList<GioHang> manggiohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaiSP();
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL, false);
//            recyclerView.setLayoutManager(linearLayoutManager);
            GetDuLieuSPmoi();
            CatchOnItemListView();
        }else{
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }
    }

    private void CatchOnItemListView() {
        lstMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast_Short(getApplicationContext(), "bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idloaisp",arrayLoaisp.get(i).getId());
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast_Short(getApplicationContext(), "bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idloaisp",3);
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast_Short(getApplicationContext(), "bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, XemDonHangActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.showToast_Short(getApplicationContext(), "bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this, XemDonHangActivity.class);

                            startActivity(intent);
                        }else {
                            CheckConnection.showToast_Short(getApplicationContext(), "bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDuLieuSPmoi() {
        API.apiService.getSanPham().enqueue(new Callback<DataResponse<List<SanPham>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<SanPham>>> call, retrofit2.Response<DataResponse<List<SanPham>>> response) {
                if (response.body().getResult() != null){
                    List<SanPham> sanpham = response.body().getResult();
                    spAdapter.setData(sanpham);
                    recyclerView.setAdapter(spAdapter);
                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<SanPham>>> call, Throwable t) {

            }
        });
    }

    private void GetDuLieuLoaiSP() {
        API.apiService.getLoaiSP().enqueue(new Callback<DataResponse<List<LoaiSanPham>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<LoaiSanPham>>> call, Response<DataResponse<List<LoaiSanPham>>> response) {
                if (response.body().getResult() != null){
                    arrayLoaisp = response.body().getResult();
                    loaispAdapter = new LoaispAdapter(getApplicationContext(), arrayLoaisp);
                    lstMain.setAdapter(loaispAdapter);

                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<LoaiSanPham>>> call, Throwable t) {

            }
        });
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png");
        mangquangcao.add("https://cdnv2.tgdd.vn/mwg-static/common/Banner/f6/b2/f6b2b1ccc5b1e5da5056d53a5b4b1521.png");
        mangquangcao.add("https://cdnv2.tgdd.vn/mwg-static/common/Banner/e6/f0/e6f0014898ce2cf08532887e8d103406.png");
        for (int i = 0;i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);

            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_rigth);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (manggiohang == null){
            manggiohang = new ArrayList<>();
        }else {
            badge.setText(String.valueOf(manggiohang.size()));
        }

    }

    private void AnhXa(){
        toolbar = findViewById(R.id.tbMain);
        viewFlipper = findViewById(R.id.vfMain);
        recyclerView = findViewById(R.id.reSanPham);
        navigationView = findViewById(R.id.navMain);
        lstMain = findViewById(R.id.lstMain);
        drawerLayout = findViewById(R.id.drawerlayout);
        badge = findViewById(R.id.menu_sl);
        if (manggiohang == null){
            manggiohang = new ArrayList<>();
        }else {
            badge.setText(String.valueOf(manggiohang.size()));
        }
        frameLayout = findViewById(R.id.cart);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GioHangActivity.class));
            }
        });

        arrayLoaisp = new ArrayList<>();
        //arrayLoaisp.add(0, new LoaiSanPham(0, "Trang chinh", "https://cdn-icons-png.flaticon.com/256/25/25694.png"));

        loaispAdapter = new LoaispAdapter(this, arrayLoaisp);
        lstMain.setAdapter(loaispAdapter);

        spAdapter = new SanPhamAdapter(this);
        recyclerView.setHasFixedSize(true);
        //Định nghĩa recycleView thành listview
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
    }

}