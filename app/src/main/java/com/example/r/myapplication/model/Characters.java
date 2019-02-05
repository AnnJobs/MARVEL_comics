package com.example.r.myapplication.model;

import com.example.r.myapplication.data.MarvelAPI;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public class Characters extends LoadingObject {

    static LoadingObjetctFactory getLoadingObjetctFactory(){
        return new LoadingObjetctFactory() {
            @Override
            public Call load(int limit, int offset, MarvelAPI api) {
                return api.loadHeroes(limit, offset);
            }

            @Override
            public Call load(int limit, int offset, String searchedName, MarvelAPI api) {
                return api.loadSearchedCharacters(searchedName, limit, offset);
            }

            @Override
            public Call load(int limit, int offset, int characterId, MarvelAPI api) {
                return null;
            }
        };
    }

    @SerializedName("name")
    public String name;

    @Override
    public String getImgAspectRatio() {
        return "standard_fantastic";
    }
}
