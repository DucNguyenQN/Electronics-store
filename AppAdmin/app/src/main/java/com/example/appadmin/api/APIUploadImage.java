package com.example.appadmin.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIUploadImage {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    APIUploadImage apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.94:9090/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(APIUploadImage.class);
    @Multipart
    @POST("api/upload/image")
    Call<String> uploadImage(@Part MultipartBody.Part file);
}
