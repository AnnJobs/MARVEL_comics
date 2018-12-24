package com.example.r.myapplication.data;

import com.example.r.myapplication.Const;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarvelService {

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private MarvelAPI marvelAPI;

    public MarvelService() {
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(provideInterceptor())
                .addInterceptor(new AuthAInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        marvelAPI = retrofit.create(MarvelAPI.class);

    }

    private HttpLoggingInterceptor provideInterceptor(){
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }


    public MarvelAPI getApi(){
        return marvelAPI;
    }


}
