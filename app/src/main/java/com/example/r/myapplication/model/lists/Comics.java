package com.example.r.myapplication.model.lists;

import android.util.Log;

import com.example.r.myapplication.data.MarvelAPI;
import com.example.r.myapplication.data.MarvelService;
import com.example.r.myapplication.loaders.listLoaders.ListLoader;
import com.example.r.myapplication.model.RepresentativeCover;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public class Comics extends RepresentativeCover {

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
                return api.loadComics(limit, offset);
            }

            @Override
            public Call load(int limit, int offset, String searchedName, MarvelAPI api) {
                return api.loadComics(searchedName, limit, offset);
            }

            @Override
            public Call loadListInItem(int limit, int offset, int itemType, int itemId, MarvelAPI api) {
                switch (itemType) {
                    case ListLoader.CHARACTER_TYPE:
                        Log.d("llllll", "loadListInItem: c");
                        return api.loadComics(itemId, limit, offset);
                    case ListLoader.EVENTS_TYPE:
                        return api.loadComicsInEvent(itemId, limit, offset);
                    case ListLoader.SERIES_TYPE:
                        return api.loadComicsInSeries(itemId, limit, offset);
                    case ListLoader.CREATOR_TYPE:
                        return api.loadCreatorsComics(itemId, limit, offset);
                    default:
                        return null;
                }
            }

            @Override
            public Call loadInfo(int id, MarvelAPI api) {
                return api.loadComicInfo(id);
            }


        };
    }
}
