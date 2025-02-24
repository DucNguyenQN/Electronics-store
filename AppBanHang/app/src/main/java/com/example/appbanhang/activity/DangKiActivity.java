package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.appbanhang.retrofit.API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKiActivity extends AppCompatActivity {
    EditText edtGmail, edtUsername, edtPass, edtPhone;
    Button btnDangki;
    TextView txtDangnhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ki);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        innitView();
        txtDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangKiActivity.this, DangNhapActivity.class));
            }
        });
        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtGmail.getText().toString().trim().isEmpty()) {
                    Toast.makeText(DangKiActivity.this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
                } else if (edtUsername.getText().toString().trim().isEmpty()) {
                    Toast.makeText(DangKiActivity.this, "Vui lòng nhập tên", Toast.LENGTH_SHORT).show();
                } else if (edtPass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(DangKiActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (edtPhone.getText().toString().trim().isEmpty()) {
                    Toast.makeText(DangKiActivity.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }else {
                    API.apiService.dangki(edtGmail.getText().toString().trim(),
                                            edtPass.getText().toString().trim(),
                                            edtUsername.getText().toString().trim(),
                                            edtPhone.getText().toString().trim()).enqueue(new Callback<DataResponse>() {
                        @Override
                        public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                            if (response.body().isSuccess()){
                                Toast.makeText(DangKiActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DangKiActivity.this, DangNhapActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<DataResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void innitView() {
        edtGmail = findViewById(R.id.regisUser);
        edtUsername = findViewById(R.id.regisName);
        edtPass = findViewById(R.id.regisPassword);
        edtPhone = findViewById(R.id.regisPhone);
        btnDangki = findViewById(R.id.btnregister);
        txtDangnhap  = findViewById(R.id.txtlogin);
    }
}