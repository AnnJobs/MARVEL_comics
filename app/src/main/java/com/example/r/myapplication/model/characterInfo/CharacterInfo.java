package com.example.r.myapplication.model.characterInfo;

import com.example.r.myapplication.model.Image;
import com.example.r.myapplication.model.Stories;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterInfo {

    @SerializedName("characterId")
    public int characterId;

    @SerializedName("thumbnail")
    public Image charImage;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;
}
