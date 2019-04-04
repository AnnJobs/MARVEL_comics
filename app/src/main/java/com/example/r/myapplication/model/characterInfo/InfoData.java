package com.example.r.myapplication.model.characterInfo;

import com.example.r.myapplication.model.LoadingObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoData {

    @SerializedName("results")
    public List<Info> information;
}
