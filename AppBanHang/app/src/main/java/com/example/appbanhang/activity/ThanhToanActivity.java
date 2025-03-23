package com.example.appbanhang.activity;

import static com.example.appbanhang.activity.MainActivity.manggiohang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.exifinterface.media.ExifInterface;

import com.example.appbanhang.R;
import com.example.appbanhang.model.DataResponse;
import com.example.appbanhang.model.GioHang;
import com.example.appbanhang.model.RequestBody;
import com.example.appbanhang.model.User;
import com.example.appbanhang.retrofit.API;
import com.example.appbanhang.retrofit.Apibanhang;
import com.example.appbanhang.retrofit.PushNotification;
import com.example.appbanhang.ulttil.Ultils;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtTongTien, txtSdt, txtEmail;
    Button btnDatHang;
    EditText edtDiaChi;
    int totalitem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanh_toan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        innitView();
        countItem();
        innitControl();
    }
    private void countItem() {
        totalitem = 0;
        for (int i = 0; i < Ultils.mangmuahang.size(); i++){
            totalitem +=  Ultils.mangmuahang.get(i).getSoluongsp();
        }
    }
    private void innitControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long tongtien = getIntent().getLongExtra("tongtien",0);
        txtTongTien.setText(decimalFormat.format(tongtien)+"");
        txtEmail.setText(Ultils.user_current.getEmail());
        txtSdt.setText(Ultils.user_current.getSdt());
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtDiaChi.getText().toString().trim().isEmpty()){
                    Toast.makeText(ThanhToanActivity.this, "Vui lòng nhập địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
                }else{
                    String str_email = Ultils.user_current.getEmail();
                    String str_sdt = Ultils.user_current.getSdt();
                    int id = Ultils.user_current.getId();
                    Log.d("test",new Gson().toJson( Ultils.mangmuahang));
                    API.apiService.createOrder(str_sdt, str_email,tongtien+"", id,edtDiaChi.getText().toString(),totalitem,new Gson().toJson( Ultils.mangmuahang)).enqueue(new Callback<DataResponse>() {
                        @Override
                        public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                            pushNotifictionUser();
                            Toast.makeText(ThanhToanActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                            //manggiohang.remove()
                            for (int i= 0;i < Ultils.mangmuahang.size(); i++){
                                GioHang gio = Ultils.mangmuahang.get(i);
                                if (manggiohang.contains(gio)){
                                    manggiohang.remove(gio);
                                }
                            }
                            Ultils.mangmuahang.clear();
                            Paper.book().write("gio_hang", manggiohang);
                            startActivity(new Intent(ThanhToanActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(Call<DataResponse> call, Throwable t) {
                            Toast.makeText(ThanhToanActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                            Log.d("Loi", t.getMessage());
                        }
                    });
                }
            }
        });
    }

    private void pushNotifictionUser() {
        API.apiService.gettoken(1).enqueue(new Callback<DataResponse<List<User>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<User>>> call, Response<DataResponse<List<User>>> response) {
                if (response.body().isSuccess() ){
                    for (User user : response.body().getResult()){
                        RequestBody requestBody = new RequestBody(user.getToken(), "Thong bao", "Ban co don hang moi");
                        PushNotification.apiService.sendNotification(requestBody).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(ThanhToanActivity.this, "Gui thong bao thanh cong", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(ThanhToanActivity.this, "Gui thong bao that bai", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<User>>> call, Throwable t) {

            }
        });

    }

    private void innitView() {
        toolbar = findViewById(R.id.toolbarTT);
        txtTongTien  = findViewById(R.id.txtTongTien);
        txtEmail = findViewById(R.id.txtEmail);
        txtSdt = findViewById(R.id.txtSDT);
        btnDatHang  = findViewById(R.id.btnDatHang);
        edtDiaChi = findViewById(R.id.edtDiachi);
    }
}