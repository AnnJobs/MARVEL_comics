package com.example.r.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListData<T extends LoadingObject> {
    @SerializedName("total")
    public int total;

    @SerializedName("results")
    public List<T> list;
}
