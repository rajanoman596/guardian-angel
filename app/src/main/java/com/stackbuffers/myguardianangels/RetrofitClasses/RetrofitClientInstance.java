package com.stackbuffers.myguardianangels.RetrofitClasses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static final String BASE_URL = "https://myguardianangels.app/newtest/api/";
    private static RetrofitClientInstance mInstance;
    private static Retrofit retrofit;

    private  OkHttpClient.Builder builder = new OkHttpClient.Builder();
    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();



    RetrofitClientInstance() {


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder.build())
                .build();
    }

    public static synchronized RetrofitClientInstance getInstance(){
        if(mInstance==null)
            mInstance = new RetrofitClientInstance();
        return mInstance;

    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}