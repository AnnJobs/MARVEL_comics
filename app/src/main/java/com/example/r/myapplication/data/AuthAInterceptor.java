package com.example.r.myapplication.data;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;

public class AuthAInterceptor implements Interceptor {

    private static final String TIMESTAMP_PARAM = "ts";
    private static final String PUBLIC_PARAM="apikey";
    private static final String HASH_PARAM = "hash";

    private static final String PUBLIC_API_KEY ="98084243d6c2d68c6392f547033feeea";
    private static final String PRIVATE_API_KEY = "3d9aab7e9ce692bb24f13cead7541ae33c82718a";

    @Override
    public Response intercept(Chain chain) throws IOException {

        String timestamp = String.valueOf(System.currentTimeMillis()/1000);
        String hash = HashMD5.hash(timestamp + PRIVATE_API_KEY + PUBLIC_API_KEY);

        HttpUrl httpUrl = chain.request().url().newBuilder()
            .addQueryParameter(TIMESTAMP_PARAM,timestamp)
            .addQueryParameter(PUBLIC_PARAM,PUBLIC_API_KEY)
            .addQueryParameter(HASH_PARAM,hash)
            .build();


        return chain.proceed(
                chain.request().newBuilder()
                .url(httpUrl)
                .build()
        );
    }
}
