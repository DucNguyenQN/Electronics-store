package com.example.appbanhang.retrofit;

import android.database.Observable;

import com.example.appbanhang.model.LoaiSanPham;
import com.example.appbanhang.model.SanPham;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Apibanhang {

    @POST("getsanpham.php")
    @FormUrlEncoded
    Observable<SanPham> getSanPham(
            @Field("page") int page,
            @Field("idsp") int loai
    );
}
