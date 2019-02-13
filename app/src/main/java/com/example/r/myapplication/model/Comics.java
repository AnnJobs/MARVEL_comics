package com.example.r.myapplication.model;

import com.example.r.myapplication.data.MarvelAPI;
import com.example.r.myapplication.data.MarvelService;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public class Comics extends RepresentativeCover {

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
                return api.loadComics(limit, offset);
            }

            @Override
            public Call load(int limit, int offset, String searchedName, MarvelAPI api) {
                return api.loadComics(searchedName, limit, offset);
            }

            @Override
            public Call load(int limit, int offset, int characterId, MarvelAPI api) {
                return api.loadComics(characterId, limit, offset);
            }
        };
    }
}
