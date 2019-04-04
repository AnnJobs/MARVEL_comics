package com.example.r.myapplication.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.r.myapplication.R;
import com.example.r.myapplication.adapters.ListItemAdapter;
import com.example.r.myapplication.loaders.listLoaders.ListLoader;
import com.example.r.myapplication.model.lists.Characters;
import com.example.r.myapplication.model.lists.Events;
import com.example.r.myapplication.model.lists.Series;

public class ComicInfoFragment extends GeneralInfoFragment {

    private ListItemAdapter<Characters> charactersAdapter;
    private ListLoader<Characters> charactersLoader;

    private ListLoader<Events> eventsLoader;
    private ListItemAdapter<Events> eventsAdapter;

    public static ComicInfoFragment newInstance(int charId) {
        Bundle args = new Bundle();
        args.putInt(ARG_ITEM_ID, charId);
        args.putInt(ARG_ITEM_TYPE, ListLoader.COMICS_TYPE);

        ComicInfoFragment comicFragment = new ComicInfoFragment();
        comicFragment.setArguments(args);
        return comicFragment;
    }

    @Override
    public void makeAdapters() {
        charactersAdapter = newAdapter(ListLoader.CHARACTER_TYPE);
        eventsAdapter = newAdapter(ListLoader.EVENTS_TYPE);

        listOfRecyclerView.get(0).setAdapter(charactersAdapter);
        listOfRecyclerView.get(1).setAdapter(eventsAdapter);
    }

    @Override
    public void loadLists() {
        charactersLoader.loadItems();
        eventsLoader.loadItems();
    }

    @Override
    public void findRV(View view) {
        listOfRecyclerView.add((RecyclerView) view.findViewById(R.id.characters_recycler_view));
        listOfRecyclerView.add((RecyclerView) view.findViewById(R.id.events_recycler_view));
    }

    @Override
    public void makeLoaders(View view) {
        charactersLoader = newListLoader(ListLoader.CHARACTER_TYPE, view, listOfRecyclerView.get(0));
        eventsLoader = newListLoader(ListLoader.EVENTS_TYPE, view, listOfRecyclerView.get(1));
    }
}
