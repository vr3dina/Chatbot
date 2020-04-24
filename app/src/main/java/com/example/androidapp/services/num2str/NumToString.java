package com.example.androidapp.services.num2str;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NumToString implements Serializable {
    @SerializedName("status")
    @Expose
    public Integer status;

    @SerializedName("str")
    @Expose
    public String convertedNumber;
}
