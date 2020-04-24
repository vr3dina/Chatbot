package com.example.androidapp.services.num2str;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NumToStringAPI {
    @GET("json/convert/num2str")
    Call<NumToString> getStringConvertedNumber(@Query("num") Integer n);
}
