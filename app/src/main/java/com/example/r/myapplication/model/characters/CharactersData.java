package com.example.r.myapplication.model.characters;

import com.example.r.myapplication.model.characters.Characters;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharactersData {

    @SerializedName("total")
    public int total;

    @SerializedName("offset")
    public int offset;

    @SerializedName("limit")
    public int limit;

    @SerializedName("results")
    public List<Characters> characters;
}
