package com.example.appbanhang.retrofit;

import android.provider.ContactsContract;

import com.example.appbanhang.model.DataResponse;
import com.example.appbanhang.model.HistoryResponse;
import com.example.appbanhang.model.LoaiSanPham;
import com.example.appbanhang.model.SanPham;
import com.example.appbanhang.model.User;
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
import retrofit2.http.Query;

public interface API {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient().create();
    API apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.94:8080/banhang/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(API.class);


    @GET("sanphammoi.php")
    Call<DataResponse<List<SanPham>>> getSanPham();

    @GET("getloaisp.php")
    Call<DataResponse<List<LoaiSanPham>>> getLoaiSP();

    @FormUrlEncoded
    @POST("getsptheoloai.php")
    Call<DataResponse<List<SanPham>>> getSPLoai(@Field("page") int page,
                                                @Field("loai") int loai);

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
    @POST("donhang.php")
    Call<DataResponse> createOrder(@Field("sdt") String sdt,
                                   @Field("email") String email,
                                   @Field("tongtien") String tongtien,
                                   @Field("iduser") int iduser,
                                   @Field("diachi") String diachi,
                                   @Field("soluong") int soluong,
                                   @Field("chitiet") String chitiet);

    @FormUrlEncoded
    @POST("xemdonhang.php")
    Call<DataResponse<List<HistoryResponse>>> xemdonhang(@Field("iduser") int iduser);

    @FormUrlEncoded
    @POST("updateToken.php")
    Call<DataResponse> updateToken(@Field("id") int id,
                                   @Field("token") String token);

    @FormUrlEncoded
    @POST("gettoken.php")
    Call<DataResponse<List<User>>> gettoken(@Field("status") int status);
}
