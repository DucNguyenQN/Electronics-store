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
import com.example.appbanhang.adapter.DienThoaiAdapter;
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

public class DienThoaiActivity extends AppCompatActivity {
    Toolbar toolbarDT;
    ListView lstDT;
    DienThoaiAdapter DTAdapter;
    ArrayList<SanPham> arrayListDT;
    int idDT = 0;
    int page = 0;
    View footerView;
    boolean isLoading = false;
    //static mHandler mHandler;
    boolean limitdata = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dien_thoai);
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

    private void LoadMoreData() {
        lstDT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",arrayListDT.get(i) );
                startActivity(intent);
            }
        });
        lstDT.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void GetData(int page) {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        String duongdan = Server.duongdandienthoai + String.valueOf(Page);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if(response != null && response.length() != 2){
//                    lstDT.removeFooterView(footerView);
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        for(int i = 0;i<jsonArray.length();i++){
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            arrayListDT.add(new SanPham(jsonObject.getInt("ID"),
//                                    jsonObject.getString("TENSP"),
//                                    jsonObject.getInt("GIASP"),
//                                    jsonObject.getString("HINHANHSP"),
//                                    jsonObject.getString("MOTASP"),
//                                    jsonObject.getInt("IDSP")));
//                            DTAdapter.notifyDataSetChanged();
//                        }
//                    } catch (JSONException e) {
//                        throw new RuntimeException(e);
//                    }
//                }else{
//                    limitdata = true;
//                    lstDT.removeFooterView(footerView);
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
//                params.put("idsp", String.valueOf(idDT));
//                return params;
//            }
//        };
//        requestQueue.add(stringRequest);
        API.apiService.getSPLoai(page, idDT).enqueue(new Callback<DataResponse<List<SanPham>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<SanPham>>> call, Response<DataResponse<List<SanPham>>> response) {
                if (response.body().getResult() != null){
                    List<SanPham> arrayDT = new ArrayList<>();
                    arrayDT  = response.body().getResult();
                    DTAdapter.setData(arrayDT);
                    lstDT.setAdapter(DTAdapter);
                }else {
                    Toast.makeText(DienThoaiActivity.this, "Thành cong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<SanPham>>> call, Throwable t) {
                Toast.makeText(DienThoaiActivity.this, "Loi", Toast.LENGTH_SHORT).show();
                Log.d("loi", t.getMessage());
            }
        });
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarDT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDT.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIDLoaiSP() {
        idDT = getIntent().getIntExtra("idloaisp",-1);
        Log.d("giatriloaisp",idDT +"");
    }

    private void AnhXa() {
        toolbarDT = findViewById(R.id.toolbarDienThoai);
        lstDT     = findViewById(R.id.lstDienThoai);
        arrayListDT = new ArrayList<>();
        DTAdapter = new DienThoaiAdapter(getApplicationContext());
        lstDT.setAdapter(DTAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);
        //DienThoaiActivity.mHandler = new mHandler();
    }
//    public class mHandler extends Handler{
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            switch (msg.what){
//                case 0:
//                    lstDT.addFooterView(footerView);
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
//            DienThoaiActivity.mHandler.sendEmptyMessage(0);
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            Message message = DienThoaiActivity.mHandler.obtainMessage(1);
//            DienThoaiActivity.mHandler.sendMessage(message);
//            super.run();
//        }
//    }
}