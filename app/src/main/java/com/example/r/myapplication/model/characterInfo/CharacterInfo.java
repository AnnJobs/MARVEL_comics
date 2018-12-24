package com.example.r.myapplication.model.characterInfo;

import com.google.gson.annotations.SerializedName;

public class CharacterInfo {

    @SerializedName("characterId")
    public int characterId;

    @SerializedName("thumbnail")
    public CharacterImage charImage;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;
}
