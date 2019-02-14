package com.example.r.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.r.myapplication.CharacterGridLayoutManager;
import com.example.r.myapplication.Decorator;
import com.example.r.myapplication.ItemCountLoadingDeterminant;
import com.example.r.myapplication.Listeners.OnBackPressedListener;
import com.example.r.myapplication.Listeners.PaginationScrollListener;
import com.example.r.myapplication.R;
import com.example.r.myapplication.SpanCountDefinitor;
import com.example.r.myapplication.adapters.ListItemAdapter;
import com.example.r.myapplication.loaders.listLoaders.ListLoader;
import com.example.r.myapplication.model.LoadingObject;

import java.util.ArrayList;
import java.util.List;

public class GeneralListFragment<T extends LoadingObject> extends Fragment implements OnBackPressedListener {

    protected static final String ARG_DATA_TYPE = "data_type";

    private List<T> items = new ArrayList<>();
    private List<T> savedItems = new ArrayList<>();

    private final String TAG = getClass().getName();
    private RecyclerView recyclerView;
    private ListItemAdapter<T> adapter;
    private CharacterGridLayoutManager gridLayoutManager;
    private Toolbar toolbar;
    private SearchView searchView;
    private MenuItem searchItem;
    private ProgressBar firstProgressBar;
    private ListLoader<T> loader;
    private static SelectedItemIdListener listener;
    private boolean toExit = true;
    private boolean isMainList = true;
    private AppBarLayout appBarLayout;
    private ListLoader.Listener loaderListener;

    protected static int itemsType;


    public static GeneralListFragment newInstance(int dataType) {
        Bundle args = new Bundle();
        args.putInt(ARG_DATA_TYPE, dataType);

        GeneralListFragment gListFragment = new GeneralListFragment<>();
        gListFragment.setArguments(args);
        return gListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GeneralListFragment.SelectedItemIdListener) {
            listener = (SelectedItemIdListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectedItemIdListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return inflater.inflate(R.layout.fragment_list_characters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        unpackingArguments(getArguments());

        initialiseViews(view);

        initialiseAdapter();
        initialiseLayoutManager();

        if (loader == null) {
            initListLoader();
        }

        fillInRecyclerView();

        if (items.size() == 0) {
            loader.loadItems();
            Log.d("problem", "had loaded first");
        } else {
            adapter.setItems(items);
            firstProgressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            Log.d("problem", "had added in list");

        }

    }

    private void initialiseViews(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.characters_title);

        inflateMenu();

        recyclerView = view.findViewById(R.id.characters_recycler_view);
        firstProgressBar = view.findViewById(R.id.first_progress_bar);

        appBarLayout = view.findViewById(R.id.app_bar);
    }

    protected void unpackingArguments(Bundle args){
        if (args != null && args.containsKey(ARG_DATA_TYPE))
        itemsType = args.getInt(ARG_DATA_TYPE);
    }

    protected ListLoader.Listener<T> getListLoaderListener() {

        if (loaderListener != null) return loaderListener;

        loaderListener = new ListLoader.Listener<T>() {
            @Override
            public void setData(List list, boolean isEnd) {
                if (recyclerView.getVisibility() == View.GONE) {
                    recyclerView.setVisibility(View.VISIBLE);
                    firstProgressBar.setVisibility(View.GONE);
                }
                items.addAll(list);
                Log.d(TAG, "setCharactersOnRecyclerView: ");
                if (isEnd) adapter.listEnded();
                adapter.setItems(list);
            }
            };
        return loaderListener;
    }

    private void initialiseLayoutManager() {
        SpanCountDefinitor.determineSpanCount(getContext());

        gridLayoutManager = new CharacterGridLayoutManager(getContext(), SpanCountDefinitor.getSpanCount(),
                new CharacterGridLayoutManager.ItemTypeListener() {
                    @Override
                    public int getItemType(int i) {
                        return adapter.getItemViewType(i);
                    }
                });
        gridLayoutManager.setSpan();
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    private void initialiseAdapter() {

            adapter = new ListItemAdapter<>(new ListItemAdapter.SelectedItemIdListener() {
                @Override
                public void onSelectedItemId(int id) {
                    listener.onSelectedItemId(id);
                    Log.i("prb", "clicked");
                }
            });
        recyclerView.setAdapter(adapter);
    }

    private void initPaginationScrollListener() {
        recyclerView.addOnScrollListener(new PaginationScrollListener(new PaginationScrollListener.Listener() {
            @Override
            public void loadMore() {
                loader.loadMore();
            }
        }));
    }

    private void fillInRecyclerView() {
        initPaginationScrollListener();

        recyclerView.addItemDecoration(new Decorator(1));
    }

    protected void inflateMenu() {
        toolbar.inflateMenu(R.menu.toolbar_menu_list);

        searchItem = toolbar.getMenu().findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (isMainList) {
                    toNewList(items);
                } else {
                    toNewList();
                }
                initListLoader(s);
                isMainList = false;
                loader.loadItems();

                toExit = false;
                recyclerView.setVisibility(View.GONE);
                firstProgressBar.setVisibility(View.VISIBLE);
                appBarLayout.setExpanded(true);
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void toNewList(List<T> toSaveList) {
        savedItems.addAll(toSaveList);
        toNewList();
    }

    private void toNewList() {
        clearList();
        initialiseLayoutManager();
        initPaginationScrollListener();
    }

    private void clearList() {
        adapter.clear();
        items.clear();
    }

    protected void initListLoader(){
        loader = new ListLoader<>(getListLoaderListener(), itemsType);
    }

    private void initListLoader(String s){
        loader = new ListLoader<>(getListLoaderListener(), s, itemsType);
    }

    protected void setLoader(ListLoader<T> listLoader){
        loader = listLoader;
    }

    @Override
    public void onBackPressed() {

        if (!isMainList) {
            toNewList();
            loader = new ListLoader<>(getListLoaderListener(), new ItemCountLoadingDeterminant(items.size()), itemsType);
            initPaginationScrollListener();
            items.addAll(savedItems);
            adapter.setItems(items);
            Log.d("offsets", "onBackPressed: character.size = " + loader.getItemCountLoadingDeterminant().checkOffset());
            appBarLayout.setExpanded(true);
            searchItem.collapseActionView();

            if (!searchItem.isActionViewExpanded()) toExit = true;
        } else if (searchItem.isActionViewExpanded()) searchItem.collapseActionView();
    }

    @Override
    public boolean needToExit() {
        return toExit;
    }

    public interface SelectedItemIdListener {
        void onSelectedItemId(int id);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (searchView != null)
        searchView.clearFocus();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        itemsType = 0;
    }
}
