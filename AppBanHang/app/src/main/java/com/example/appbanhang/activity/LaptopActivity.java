package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.LaptopAdapter;
import com.example.appbanhang.model.DataResponse;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.retrofit.API;
import com.example.appbanhang.ulttil.CheckConnection;
import com.example.appbanhang.ulttil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbarLT;
    ListView lstLT;
    LaptopAdapter LTAdapter;
    ArrayList<SanPham> arrayListLT;
    int idLT;
    int page = 0;
    View footerView;
    boolean isLoading = false;
    //static mHandler mHandler;
    boolean limitdata = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_laptop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AnhXa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIDLoaiSP();
            ActionToolBar();
            GetData(page);
            LoadMoreData();
        }else{
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại Internet");
            finish();
        }
    }
    private void AnhXa() {
        toolbarLT = findViewById(R.id.toolbarLaptop);
        lstLT     = findViewById(R.id.lstLaptop);
        arrayListLT = new ArrayList<>();
        LTAdapter = new LaptopAdapter(getApplicationContext());
        lstLT.setAdapter(LTAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);
        //mHandler = new mHandler();
    }
    private void GetIDLoaiSP() {
        idLT = getIntent().getIntExtra("idloaisp",-1);
        //Toast.makeText(this, idLT+"", Toast.LENGTH_SHORT).show();
        Log.d("giatriloaisp",idLT +"");
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbarLT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLT.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void GetData(int Page) {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        String duongdan = Server.duongdanlaptop + String.valueOf(Page);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if(response != null && response.length() != 2){
//                    lstLT.removeFooterView(footerView);
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        for(int i = 0;i<jsonArray.length();i++){
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
////                            arrayListLT.add(new SanPham(jsonObject.getInt("ID"),
////                                    jsonObject.getString("TENSP"),
////                                    jsonObject.getInt("GIASP"),
////                                    jsonObject.getString("HINHANHSP"),
////                                    jsonObject.getString("MOTASP"),
////                                    jsonObject.getInt("IDSP")));
//                            LTAdapter.notifyDataSetChanged();
//                        }
//                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
//                }else{
//                    limitdata = true;
//                    lstLT.removeFooterView(footerView);
//                    CheckConnection.showToast_Short(getApplicationContext(), "Đã hết dữ liệu");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("AAA",error.toString());
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String,String> params = new HashMap<>();
//                params.put("idsp", String.valueOf(idLT));
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
        API.apiService.getSPLoai(Page, idLT).enqueue(new Callback<DataResponse<List<SanPham>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<SanPham>>> call, Response<DataResponse<List<SanPham>>> response) {
                if (response.body().getResult() != null) {
                    List<SanPham> arrayLT = new ArrayList<>();
                    arrayLT = response.body().getResult();
                    LTAdapter.setData(arrayLT);
                    lstLT.setAdapter(LTAdapter);
                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<SanPham>>> call, Throwable t) {
                Toast.makeText(LaptopActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void LoadMoreData() {
        lstLT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",arrayListLT.get(i) );
                startActivity(intent);
            }
        });
        lstLT.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirtsItem, int VisibleItem, int TotalItem) {
//                if(FirtsItem + VisibleItem == TotalItem && TotalItem != 0 && isLoading == false && limitdata == false){
//                    isLoading = true;
//                    ThreadData threadData = new ThreadData();
//                    threadData.start();
//                }
            }
        });
    }
//    public class mHandler extends Handler {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            switch (msg.what){
//                case 0:
//                    lstLT.addFooterView(footerView);
//                    break;
//                case 1:
//                    GetData(++page);
//                    isLoading = false;
//            }
//            super.handleMessage(msg);
//        }
//    }
//    public class ThreadData extends Thread{
//        @Override
//        public void run() {
//            mHandler.sendEmptyMessage(0);
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            Message message = mHandler.obtainMessage(1);
//            mHandler.sendMessage(message);
//            super.run();
//        }
//    }
}