package com.example.r.myapplication.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.r.myapplication.R;
import com.example.r.myapplication.adapters.ListItemAdapter;
import com.example.r.myapplication.loaders.listLoaders.ListLoader;
import com.example.r.myapplication.model.lists.Characters;
import com.example.r.myapplication.model.lists.Comics;
import com.example.r.myapplication.model.lists.Events;
import com.example.r.myapplication.model.lists.Series;

public class EventInfoFragment extends GeneralInfoFragment {
    private ListItemAdapter<Characters> characterAdapter;
    private ListLoader<Characters> characterLoader;

    private ListLoader<Series> seriesLoader;
    private ListItemAdapter<Series> seriesAdapter;

    private ListLoader<Comics> comicsLoader;
    private ListItemAdapter<Comics> comicsAdapter;

    public static EventInfoFragment newInstance(int charId) {
        Bundle args = new Bundle();
        args.putInt(ARG_ITEM_ID, charId);
        args.putInt(ARG_ITEM_TYPE, ListLoader.EVENTS_TYPE);

        EventInfoFragment eventInfoFragment = new EventInfoFragment();
        eventInfoFragment.setArguments(args);
        return eventInfoFragment;
    }

    @Override
    public void makeAdapters() {
        Log.d("checki", "makeAdapters: ");
        characterAdapter = newAdapter(ListLoader.CHARACTER_TYPE);
        comicsAdapter = newAdapter(ListLoader.COMICS_TYPE);
        seriesAdapter = newAdapter(ListLoader.SERIES_TYPE);

        listOfRecyclerView.get(0).setAdapter(comicsAdapter);
        listOfRecyclerView.get(1).setAdapter(seriesAdapter);
        listOfRecyclerView.get(2).setAdapter(characterAdapter);
    }

    @Override
    public void loadLists() {
        comicsLoader.loadItems();
        seriesLoader.loadItems();
        characterLoader.loadItems();
        Log.d("checki", "loadLists: ");
    }

    @Override
    public void findRV(View view) {
        listOfRecyclerView.add((RecyclerView) view.findViewById(R.id.comics_recycler_view));
        listOfRecyclerView.add((RecyclerView) view.findViewById(R.id.series_recycler_view));
        listOfRecyclerView.add((RecyclerView) view.findViewById(R.id.characters_recycler_view));
        Log.d("checki", "findRV: ");
    }

    @Override
    public void makeLoaders(View view) {
        Log.d("checki", "makeLoaders: ");
        comicsLoader = newListLoader(ListLoader.COMICS_TYPE, view, listOfRecyclerView.get(0));
        seriesLoader = newListLoader(ListLoader.SERIES_TYPE, view, listOfRecyclerView.get(1));
        characterLoader = newListLoader(ListLoader.CHARACTER_TYPE, view, listOfRecyclerView.get(2));
    }
}
