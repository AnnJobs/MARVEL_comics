package com.example.r.myapplication.model.characterInfo;

import com.google.gson.annotations.SerializedName;

public class CharacterInfoResponse {

    @SerializedName("code")
    public int code;

    @SerializedName("data")
    public CharacterInfoData data;
}
