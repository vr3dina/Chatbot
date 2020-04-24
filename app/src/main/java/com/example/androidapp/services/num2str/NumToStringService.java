package com.example.androidapp.services.num2str;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NumToStringService {
    public static NumToStringAPI getApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://htmlweb.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(NumToStringAPI.class);
    }
}
