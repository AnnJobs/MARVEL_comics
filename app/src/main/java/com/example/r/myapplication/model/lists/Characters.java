package com.example.r.myapplication.model.lists;

import android.util.Log;

import com.example.r.myapplication.data.MarvelAPI;
import com.example.r.myapplication.loaders.listLoaders.ListLoader;
import com.example.r.myapplication.model.LoadingObject;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public class Characters extends LoadingObject {

    public static LoadingObjetctFactory getLoadingObjetctFactory() {
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
            public Call loadListInItem(int limit, int offset, int itemType,
                                       int itemId, MarvelAPI api) {
                switch (itemType) {
                    case ListLoader.COMICS_TYPE:
                        return api.loadCharactersInComic(itemId, limit, offset);
                    case ListLoader.EVENTS_TYPE:
                        return api.loadCharactersInEvent(itemId, limit, offset);
                    case ListLoader.SERIES_TYPE:
                        return api.loadCharactersInSeries(itemId, limit, offset);
                    default:
                        Log.d("llllll", "loadListInItem: year");
                        return null;
                }
            }

            @Override
            public Call loadInfo(int id, MarvelAPI api) {
                return api.loadCharacterInfo(id);
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
