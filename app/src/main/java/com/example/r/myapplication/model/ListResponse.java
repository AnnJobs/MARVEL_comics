package com.example.r.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class ListResponse<T extends LoadingObject> {

    @SerializedName("data")
    public ListData<T> data;
}
