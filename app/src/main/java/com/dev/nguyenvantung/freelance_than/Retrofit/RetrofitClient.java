package com.dev.nguyenvantung.freelance_than.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseurl) {
        OkHttpClient builder = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS) //thoi gian doc du lieu tu server
                .writeTimeout(5000, TimeUnit.MILLISECONDS)  //thoi gian ghi du lieu len server
                .connectTimeout(10000, TimeUnit.MILLISECONDS) //thoi gian cho va ngat ket noi
                .retryOnConnectionFailure(true) //khoi phuc lai ket noi neu ket noi bi gian doan
                .build();

        Gson gson = new GsonBuilder().setLenient().create(); // giup convert tot hon

        retrofit = new Retrofit.Builder()
                .baseUrl(baseurl) //1 duong dan co so de khoi tao retrofit
                .client(builder)  //dung de kiem soat nhung gi minh da set o OkHttp
                .addConverterFactory(GsonConverterFactory.create(gson)) ///khi co du lieu json, va muon chuyen du lieu sang gson, va co
                //chuc nang chuyen json ve bien cua java thong qua tool json
                .build();

        return retrofit;
    }
}
