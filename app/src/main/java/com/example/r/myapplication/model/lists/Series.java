package com.example.r.myapplication.model.lists;

import android.util.Log;

import com.example.r.myapplication.data.MarvelAPI;
import com.example.r.myapplication.loaders.listLoaders.ListLoader;
import com.example.r.myapplication.model.RepresentativeCover;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public class Series extends RepresentativeCover {

    @SerializedName("title")
    public String title;

    @Override
    public String getTitle() {
        return title;
    }

    public static LoadingObjetctFactory getLoadingObjetctFactory() {
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
            public Call loadListInItem(int limit, int offset, int itemType, int itemId, MarvelAPI api) {
                switch (itemType) {
                    case ListLoader.CHARACTER_TYPE:
                        Log.d("llllll", "loadListInItem: s");
                        return api.loadSeries(itemId, limit, offset);
                    case ListLoader.EVENTS_TYPE:
                        return api.loadSeriesInEvent(itemId, limit, offset);
                    case ListLoader.CREATOR_TYPE:
                        return api.loadCreatorsSeries(itemId, limit, offset);
                    default:
                        return null;
                }
            }

            @Override
            public Call loadInfo(int id, MarvelAPI api) {
                return api.loadSeriesInfo(id);
            }
        };
    }
}
