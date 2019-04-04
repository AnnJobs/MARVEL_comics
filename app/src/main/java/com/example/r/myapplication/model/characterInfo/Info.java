package com.example.r.myapplication.model.characterInfo;

import com.example.r.myapplication.model.Image;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("characterId")
    public int characterId;

    @SerializedName("thumbnail")
    public Image charImage;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;
}
