package com.example.r.myapplication.loaders.listLoaders;

import android.util.Log;

import com.example.r.myapplication.ItemCountLoadingDeterminant;
import com.example.r.myapplication.data.MarvelService;
import com.example.r.myapplication.model.Characters;
import com.example.r.myapplication.model.Comics;
import com.example.r.myapplication.model.ListResponse;
import com.example.r.myapplication.model.LoadingObject;
import com.example.r.myapplication.model.LoadingObjectsManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListLoader<T extends LoadingObject> {

    public static final int CHARACTER_TYPE = 0;
    public static final int COMICS_TYPE = 1;
    public static final int EVENTS_TYPE = 2;
    public static final int SERIES_TYPE = 3;

    private int itemType;

    Listener<T> listener;
    MarvelService marvelService = new MarvelService();
    ItemCountLoadingDeterminant itemCountLoadingDeterminant;
    LoadingObjectsManager loadingObjectsManager = new LoadingObjectsManager();

    String searchedName;
    int characterId;

    public ListLoader(Listener<T> listener, int itemType) {
        this.itemType = itemType;
        this.listener = listener;
        itemCountLoadingDeterminant = new ItemCountLoadingDeterminant(0);
    }

    public ListLoader(Listener<T> listener, String searchedName, int itemType) {
        this(listener, itemType);
        this.searchedName = searchedName;
    }

    public ListLoader(Listener<T> listener, int characterId, int itemType) {
        this(listener, itemType);
        this.characterId = characterId;
    }

    public ListLoader(Listener<T> listener, ItemCountLoadingDeterminant itemCountLoadingDeterminant, int itemType) {
        this.itemType = itemType;
        this.listener = listener;
        this.itemCountLoadingDeterminant = itemCountLoadingDeterminant;
    }

    public ListLoader(Listener<T> listener, ItemCountLoadingDeterminant itemCountLoadingDeterminant, int characterId, int itemType) {
        this(listener, itemCountLoadingDeterminant, itemType);
        this.characterId = characterId;
    }

    public ListLoader(Listener<T> listener, ItemCountLoadingDeterminant itemCountLoadingDeterminant, String searchedName, int itemType) {
        this(listener, itemCountLoadingDeterminant, itemType);
        this.searchedName = searchedName;
    }

    private void load(int limit, int offset) {
        if (searchedName != null) {
            loadingObjectsManager.getLoadingObjectFactory(itemType).load(limit, offset, searchedName, marvelService.getApi()).enqueue(newCallback());
            Log.d("fragpr", "load: wrong call");
        } else if (characterId != 0) {
            loadingObjectsManager.getLoadingObjectFactory(itemType).load(limit, offset, characterId, marvelService.getApi()).enqueue(newCallback());
            Log.d("fragpr", "load: right call");
        } else {
            loadingObjectsManager.getLoadingObjectFactory(itemType).load(limit, offset, marvelService.getApi()).enqueue(newCallback());
        }
    }

    public void loadMore() {
        load(itemCountLoadingDeterminant.getLimit(), itemCountLoadingDeterminant.getOffset());
    }

    public void loadItems() {
        load(itemCountLoadingDeterminant.getStartLimit(), 0);
    }

    Callback<ListResponse<T>> newCallback() {
        return new Callback<ListResponse<T>>() {
            @Override
            public void onResponse(Call<ListResponse<T>> call, Response<ListResponse<T>> response) {
                if (response.isSuccessful()) {
                    if (response.body().data.total <= itemCountLoadingDeterminant.checkOffset()) {
                        Log.d("aaaa", "onResponse: total" + response.body().data.total);
                        listener.setData(response.body().data.list, true);
                    } else listener.setData(response.body().data.list, false);
                }
            }

            @Override
            public void onFailure(Call<ListResponse<T>> call, Throwable t) {
            }
        };
    }

    public ItemCountLoadingDeterminant getItemCountLoadingDeterminant() {
        return itemCountLoadingDeterminant;
    }

    public interface Listener<T extends LoadingObject> {
        void setData(List<T> list, boolean isEnd);
    }

}
