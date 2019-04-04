package com.example.r.myapplication.fragment;

import android.os.Bundle;
import android.util.Log;

import com.example.r.myapplication.loaders.listLoaders.ListLoader;
import com.example.r.myapplication.model.LoadingObject;

public class ChosenListFragment<T extends LoadingObject> extends GeneralListFragment<T> {

    private static final String ARG_CHARACTER_ID = "character_id";
    private static final String ARG_ITEM_TYPE = "item_type";

    private int chrId;
    private int itemType;

    public static <T extends LoadingObject> ChosenListFragment newInstance(int listDataType,
                                                                           int itemType, int itemId){
        Bundle args = new Bundle();
        args.putInt(ARG_DATA_TYPE, listDataType);
        args.putInt(ARG_CHARACTER_ID, itemId);
        args.putInt(ARG_ITEM_TYPE, itemType);

        ChosenListFragment<T> chosenListFragment = new ChosenListFragment<>();
        chosenListFragment.setArguments(args);
        return chosenListFragment;
    }

    @Override
    protected void unpackingArguments(Bundle args) {
        if (args != null && args.containsKey(ARG_CHARACTER_ID) && args.containsKey(ARG_DATA_TYPE)
                && args.containsKey(ARG_ITEM_TYPE)) {
            chrId = args.getInt(ARG_CHARACTER_ID);
            itemsType = args.getInt(ARG_DATA_TYPE);
            itemType = args.getInt(ARG_ITEM_TYPE);
            Log.d("types", "unpackingArguments: " + itemsType);
        }
    }

    @Override
    protected void initListLoader() {
        setLoader(new ListLoader<>(getListLoaderListener(), itemType, chrId, itemsType));
        Log.d("types", "initListLoader: " + itemsType);
    }

    @Override
    protected void inflateMenu() {}


}
