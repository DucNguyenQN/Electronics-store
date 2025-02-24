package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbanhang.R;
import com.example.appbanhang.model.DataResponse;
import com.example.appbanhang.model.User;
import com.example.appbanhang.retrofit.API;
import com.example.appbanhang.ulttil.Ultils;

import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhapActivity extends AppCompatActivity {
    EditText edtusername, edtpass;
    Button btnDangNhap;
    TextView txtDangKi;
    boolean isLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        txtDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhapActivity.this, DangKiActivity.class));
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = edtusername.getText().toString();
                String str_password= edtpass.getText().toString();
                if (str_email.trim().isEmpty()){
                    Toast.makeText(DangNhapActivity.this, "Nhập email", Toast.LENGTH_SHORT).show();
                } else if (str_password.trim().isEmpty()) {
                    Toast.makeText(DangNhapActivity.this, "Nhập password", Toast.LENGTH_SHORT).show();
                }else {
                    Paper.book().write("email", str_email);
                    Paper.book().write("password", str_password);
                    dangNhap(str_email, str_password);
                }
            }
        });
    }

    private void initView() {
        Paper.init(this);
        edtusername = findViewById(R.id.edtUser);
        edtpass = findViewById(R.id.edtPass);
        btnDangNhap = findViewById(R.id.btnLogin);
        txtDangKi = findViewById(R.id.txtregister);
        if (Paper.book().read("email") != null && Paper.book().read("password") != null){
            edtusername.setText(Paper.book().read("email"));
            edtpass.setText(Paper.book().read("password"));
            if (Paper.book().read("islogin") != null){
                boolean flag = Paper.book().read("islogin");
                if (flag){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dangNhap(Paper.book().read("email"), Paper.book().read("password"));
                        }
                    },1000);
                }
            }
        }
    }

    private void dangNhap(String str_email, String str_password) {
        API.apiService.dangnhap(edtusername.getText().toString().trim(), edtpass.getText().toString().trim()).enqueue(new Callback<DataResponse<List<User>>>() {
            @Override
            public void onResponse(Call<DataResponse<List<User>>> call, Response<DataResponse<List<User>>> response) {
                if (response.body().isSuccess()){
                    isLogin = true;
                    Paper.book().write("islogin", isLogin);
                    Ultils.user_current = response.body().getResult().get(0);
                    Toast.makeText(DangNhapActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DangNhapActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DataResponse<List<User>>> call, Throwable t) {

            }
        });
    }

}