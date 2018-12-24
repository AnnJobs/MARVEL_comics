package com.example.r.myapplication.Listeners;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.r.myapplication.fragment.CharacterListFragment;
import com.example.r.myapplication.loaders.CharactersLoader;

public class PaginationScrollListener extends RecyclerView.OnScrollListener {

    int prevFirstItem = 0;

    private final Listener listener;

    public PaginationScrollListener(Listener listener) {
        this.listener = listener;
    }


    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (dy > 0) {
            int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
            int totalItemCount = recyclerView.getLayoutManager().getItemCount();
            int pastVisibleItems = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            Log.i("SCROLL", "getChildCount = " + visibleItemCount + "     y = " + dy);
            Log.i("SCROLL", "getItemCount = " + totalItemCount);
            Log.i("SCROLL", "findFirstVisibleItemPosition = " + pastVisibleItems);

            if ((pastVisibleItems + visibleItemCount) == totalItemCount && prevFirstItem != pastVisibleItems) {
                listener.loadMore();
                prevFirstItem = pastVisibleItems;
            }
        }
        Log.i("SCROLL", "@@@@@@@@@@@@    y = " + dy);
        Log.i("SCROLL", "@@@@@@@@@@@@    x = " + dx);
    }

    public interface Listener {
        void loadMore();
    }
}
