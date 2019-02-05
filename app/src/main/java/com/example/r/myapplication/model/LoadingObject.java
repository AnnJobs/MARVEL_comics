package com.example.r.myapplication.model;

import com.example.r.myapplication.data.MarvelAPI;
import com.example.r.myapplication.data.MarvelService;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public abstract class LoadingObject {

    @SerializedName("id")
    public int id;

    @SerializedName("thumbnail")
    public Image image;

    public abstract String getImgAspectRatio();

    public static abstract class LoadingObjetctFactory {

        public abstract Call load(int limit, int offset, MarvelAPI api);

        public abstract Call load(int limit, int offset, String searchedName, MarvelAPI api);

        public abstract Call load(int limit, int offset, int characterId, MarvelAPI api);
    }
}
