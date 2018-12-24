package com.example.r.myapplication.model.characterInfo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterInfoData {

    @SerializedName("offset")
    public int offset;

    @SerializedName("limit")
    public int limit;

    @SerializedName("results")
    public List<CharacterInfo> information;
}
