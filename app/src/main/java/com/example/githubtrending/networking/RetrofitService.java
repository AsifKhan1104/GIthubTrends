package com.example.githubtrending.networking;

import androidx.annotation.Nullable;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitService {
    public static final RetrofitService INSTANCE = new RetrofitService();
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://github-trending-api.now.sh")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private RetrofitService() {
    }

    public final <S> S createService(@Nullable Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}