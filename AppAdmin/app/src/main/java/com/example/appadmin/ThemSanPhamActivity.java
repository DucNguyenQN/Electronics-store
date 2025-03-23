package com.example.appadmin;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appadmin.api.API;
import com.example.appadmin.api.APIUploadImage;
import com.example.appadmin.databinding.ActivityThemSanPhamBinding;
import com.example.appadmin.model.DataResponse;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemSanPhamActivity extends AppCompatActivity {
    private ActivityThemSanPhamBinding binding;
    int loai = 0;
    Uri mUri;
    private ActivityResultLauncher<String> imagePickerLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityThemSanPhamBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InnitData();
    }


    private void InnitData() {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("Vui lòng chọn loại");
        stringList.add("Điện thoại");
        stringList.add("Laptop");
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        binding.imgsanpham.setImageURI(uri);
                        mUri = uri;
                    }
                }
        );
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loai = position;
                Toast.makeText(ThemSanPhamActivity.this, loai+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.hinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickerLauncher.launch("image/*");
            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploagImage();
            }
        });
    }

    private void UploagImage() {
        if (mUri == null) {
            Toast.makeText(this, "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
        }else{
            String strRealPath = RealPathUtil.getRealPath(this, mUri);
            File file = new File(strRealPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part multipartbody = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

            APIUploadImage.apiService.uploadImage(multipartbody).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        String foodImage = response.body();
                        String str_ten = binding.ten.getText().toString().trim();
                        String str_gia = binding.gia.getText().toString().trim();
                        String str_mota = binding.mota.getText().toString().trim();
                        if (foodImage.isEmpty() || str_ten.isEmpty() || str_gia.isEmpty() || str_mota.isEmpty()) {
                            Toast.makeText(ThemSanPhamActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        }else{
                            API.apiService.insertsp(str_ten, str_gia, foodImage, str_mota, loai+1).enqueue(new Callback<DataResponse>() {
                                @Override
                                public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                                    if (response.isSuccessful()){
                                        Toast.makeText(ThemSanPhamActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<DataResponse> call, Throwable t) {
                                    Toast.makeText(ThemSanPhamActivity.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }else {
                        Toast.makeText(ThemSanPhamActivity.this, "Upload ảnh thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                }
            });
        }
    }
}