package com.example.r.myapplication.model.lists;

import com.example.r.myapplication.data.MarvelAPI;
import com.example.r.myapplication.model.LoadingObject;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public class Creators extends LoadingObject {
    @Override
    public String getImgAspectRatio() {
        return null;
    }

    @SerializedName("role")
    public String role;

    public static LoadingObjetctFactory getLoadingObjectFactory(){
        return new LoadingObjetctFactory() {
            @Override
            public Call load(int limit, int offset, MarvelAPI api) {
                return api.loadCreators(limit, offset);
            }

            @Override
            public Call load(int limit, int offset, String searchedName, MarvelAPI api) {
                return api.loadSearchedCreators(searchedName, limit, offset);
            }

            @Override
            public Call loadListInItem(int limit, int offset, int itemType, int itemId, MarvelAPI api) {
                return null;
            }

            @Override
            public Call loadInfo(int id, MarvelAPI api) {
                return api.loadCreator(id);
            }
        };
    }
}
