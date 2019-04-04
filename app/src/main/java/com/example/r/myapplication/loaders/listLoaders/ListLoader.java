package com.example.r.myapplication.loaders.listLoaders;

import android.util.Log;

import com.example.r.myapplication.ItemCountLoadingDeterminant;
import com.example.r.myapplication.data.MarvelService;
import com.example.r.myapplication.model.lists.ListResponse;
import com.example.r.myapplication.model.LoadingObject;
import com.example.r.myapplication.model.LoadingObjectsManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListLoader<T extends LoadingObject> {

    public static final int CHARACTER_TYPE = 5;
    public static final int COMICS_TYPE = 1;
    public static final int EVENTS_TYPE = 2;
    public static final int SERIES_TYPE = 3;
    public static final int CREATOR_TYPE = 4;

    private Listener<T> listener;
    private MarvelService marvelService = new MarvelService();
    private ItemCountLoadingDeterminant itemCountLoadingDeterminant;
    private LoadingObjectsManager loadingObjectsManager = new LoadingObjectsManager();

    private String searchedName;
    private int itemId;
    private int listItemType;
    private int itemType;

    public ListLoader(Listener<T> listener, int listItemType) {
        this.listItemType = listItemType;
        this.listener = listener;
        itemCountLoadingDeterminant = new ItemCountLoadingDeterminant(0);
    }

    public ListLoader(Listener<T> listener, String searchedName, int listItemType) {
        this(listener, listItemType);
        this.searchedName = searchedName;
    }

    public ListLoader(Listener<T> listener, int itemType, int itemId, int listItemType) {
        this(listener, listItemType);
        this.itemId = itemId;
        this.itemType = itemType;
        Log.d("llllll", "ListLoader: ");
    }

    public ListLoader(Listener<T> listener, ItemCountLoadingDeterminant itemCountLoadingDeterminant, int listItemType) {
        this.listItemType = listItemType;
        this.listener = listener;
        this.itemCountLoadingDeterminant = itemCountLoadingDeterminant;
    }

    public ListLoader(Listener<T> listener, ItemCountLoadingDeterminant itemCountLoadingDeterminant, int itemType, int itemId, int listItemType) {
        this(listener, itemCountLoadingDeterminant, listItemType);
        this.itemId = itemId;
        this.itemType = itemType;
    }

    public ListLoader(Listener<T> listener, ItemCountLoadingDeterminant itemCountLoadingDeterminant, String searchedName, int listItemType) {
        this(listener, itemCountLoadingDeterminant, listItemType);
        this.searchedName = searchedName;
    }

    private void load(int limit, int offset) {
        if (searchedName != null) {
            loadingObjectsManager.getLoadingObjectFactory(listItemType).load(limit, offset,
                    searchedName, marvelService.getApi()).enqueue(newCallback());
            Log.d("llllll", "searchName");
        } else if (itemId != 0) {
            Log.d("llllll", "load: " + itemType + "     " + listItemType);
                loadingObjectsManager.getLoadingObjectFactory(listItemType).loadListInItem(limit,
                        offset, itemType, itemId, marvelService.getApi()).enqueue(newCallback());


        } else {
            loadingObjectsManager.getLoadingObjectFactory(listItemType).load(limit, offset,
                    marvelService.getApi()).enqueue(newCallback());
            Log.d("llllll", "no way");
        }
    }

    public void loadMore() {
        load(itemCountLoadingDeterminant.getLimit(), itemCountLoadingDeterminant.getOffset());
    }

    public void loadItems() {
        Log.d("llllll", "loadItems: itemType = " + itemType + "   listType = " + listItemType);
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
