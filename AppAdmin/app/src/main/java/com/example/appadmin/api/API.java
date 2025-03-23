package com.example.appadmin.api;

import com.example.appadmin.model.DataResponse;
import com.example.appadmin.model.HistoryResponse;
import com.example.appadmin.model.SanPham;
import com.example.appadmin.model.ThongKe;
import com.example.appadmin.model.ThongKeThang;
import com.example.appadmin.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    API apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.94:8080/banhang/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API.class);

    @FormUrlEncoded
    @POST("insertsp.php")
    Call<DataResponse> insertsp(@Field("ten") String ten,
                                @Field("gia") String gia,
                                @Field("hinhanh") String hinhanh,
                                @Field("mota") String mota,
                                @Field("loai") int loai);

    @GET("sanphammoi.php")
    Call<DataResponse<List<SanPham>>> getSanPham();

    @FormUrlEncoded
    @POST("dangki.php")
    Call<DataResponse> dangki(@Field("email") String email,
                              @Field("pass") String pass,
                              @Field("username") String username,
                              @Field("sdt") String sdt,
                              @Field("uid") String uid);

    @FormUrlEncoded
    @POST("dangnhap.php")
    Call<DataResponse<List<User>>> dangnhap(@Field("email") String email,
                                            @Field("pass") String pass);

    @FormUrlEncoded
    @POST("updateToken.php")
    Call<DataResponse> updateToken(@Field("id") int id,
                                   @Field("token") String token);

    @FormUrlEncoded
    @POST("xemdonhang.php")
    Call<DataResponse<List<HistoryResponse>>> xemdonhang(@Field("iduser") int iduser);

    @GET("thongke.php")
    Call<DataResponse<List<ThongKe>>> getThongKe();

    @GET("thongkeThang.php")
    Call<DataResponse<List<ThongKeThang>>> getThongKeThang();
}
