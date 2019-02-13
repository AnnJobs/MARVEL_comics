package com.example.r.myapplication.model;

import com.example.r.myapplication.data.MarvelAPI;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public class Series extends RepresentativeCover {

    @SerializedName("title")
    public String title;

    @Override
    public String getTitle() {
        return title;
    }

    public static LoadingObjetctFactory getLoadingObjetctFactory(){
        return new LoadingObjetctFactory() {
            @Override
            public Call load(int limit, int offset, MarvelAPI api) {
                return api.loadSeries(limit, offset);
            }

            @Override
            public Call load(int limit, int offset, String searchedName, MarvelAPI api) {
                return api.loadSeries(searchedName, limit, offset);
            }

            @Override
            public Call load(int limit, int offset, int characterId, MarvelAPI api) {
                return api.loadSeries(characterId, limit, offset);
            }
        };
    }
}
