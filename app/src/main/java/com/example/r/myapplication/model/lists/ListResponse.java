package com.example.r.myapplication.model.lists;

import com.example.r.myapplication.model.LoadingObject;
import com.example.r.myapplication.model.lists.ListData;
import com.google.gson.annotations.SerializedName;

public class ListResponse<T extends LoadingObject> {

    @SerializedName("data")
    public ListData<T> data;
}
