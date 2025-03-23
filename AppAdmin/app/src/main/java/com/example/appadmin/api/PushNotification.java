package com.example.appadmin.api;

import com.example.appadmin.model.RequestBody;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PushNotification {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setLenient().create();
    PushNotification apiService = new Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PushNotification.class);

    @Headers({
            "Authorization: Bearer ya29.a0AeXRPp4z67MR24jQ0jFftXC30bDZwcUyj09beUNRJUkqLAOwd9KSoDmBomf5ldO4HJ8QfpgH4IDxRBwV5JfcAytA6CemnZMgpCDJPVAT8IPys-FTsMC7drqLTE4W4zbTzvYsX9PAQ93Uivfwpip5Tm6mJunH6x4B9C0jKRhg2AaCgYKAWISARMSFQHGX2Mi5ZHC7fVg6_Rx6nx1OW8oDg0177",
            "Content-Type: application/json"
    })
    @POST("v1/projects/my-project-f191d/messages:send")
    Call<Void> sendNotification(@Body RequestBody body);
}
