package com.example.r.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.r.myapplication.CharacterGridLayoutManager;
import com.example.r.myapplication.Decorator;
import com.example.r.myapplication.Listeners.PaginationScrollListener;
import com.example.r.myapplication.Listeners.OnBackPressedListener;
import com.example.r.myapplication.R;
import com.example.r.myapplication.SpanCountDefinitor;
import com.example.r.myapplication.adapters.CharacterAdapter;
import com.example.r.myapplication.model.characters.Characters;
import com.example.r.myapplication.loaders.CharactersLoader;

import java.util.ArrayList;
import java.util.List;

public class CharacterListFragment extends Fragment implements OnBackPressedListener {

    private List<Characters> characters = new ArrayList<>();
    private List<Characters> savedCharacters = new ArrayList<>();

    private final String TAG = getClass().getName();
    private RecyclerView recyclerView;
    private CharacterAdapter adapter;
    private CharacterGridLayoutManager gridLayoutManager;
    private Toolbar toolbar;
    private SearchView searchView;
    private MenuItem searchItem;

    private CharactersLoader charactersLoader;
    private Object savedCharactersLoader;
    private static SelectedCharacterIdListener listener;
    CollapsingToolbarLayout collaps;
    AppBarLayout appBarLayout;

    private boolean toExit = true;
    private boolean isMainList = true;

    public static CharacterListFragment newInstance() {
        return new CharacterListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SelectedCharacterIdListener) {
            listener = (SelectedCharacterIdListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectedCharacterIdListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        Log.d("dshvlsdfidbvu", "onCreateView: " + bundle);
        return inflater.inflate(R.layout.fragment_list_characters, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        collaps = view.findViewById(R.id.collapsing_toolbar);
        appBarLayout = view.findViewById(R.id.app_bar);

        initialiseViews(view);

        initialiseAdapter();
        initialiseLayoutManager();

        if (charactersLoader == null) {
            initialiseLoader();
        }

        fillInRecyclerView();

        if (characters.size() == 0) {
            charactersLoader.loadCharacters();
        } else {
            adapter.setItems(characters);
        }
    }


    private void initialiseViews(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.characters_title);

        inflateMenu();

        recyclerView = view.findViewById(R.id.characters_recycler_view);
    }

    private void initialiseLoader() {

        charactersLoader = new CharactersLoader(new CharactersLoader.Listener() {
            @Override
            public void setCharactersOnRecyclerView(List<Characters> list) {
                if (recyclerView.getVisibility() == View.GONE){
                    recyclerView.setVisibility(View.VISIBLE);
                }
                    characters.addAll(list);
                adapter.setItems(list);
            }
        });
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
    }

    private void initialiseAdapter() {
        adapter = new CharacterAdapter(new CharacterAdapter.SelectedCharacterIdListener() {
            @Override
            public void onSelectedCharacterId(int id) {
                listener.onSelectedCharacterId(id);
            }
        }, getContext());
    }

    private void fillInRecyclerView() {
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(new PaginationScrollListener.Listener() {
            @Override
            public void loadMore() {
                charactersLoader.loadMore();
            }
        }));
        recyclerView.addItemDecoration(new Decorator(1));
    }

    private void inflateMenu() {
        toolbar.inflateMenu(R.menu.toolbar_menu);

        searchItem = toolbar.getMenu().findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (isMainList) {
                    toNewList(characters, charactersLoader);
                } else {
                    toNewList();
                }
                charactersLoader.setSearchedName(s);
                isMainList = false;
                charactersLoader.loadCharacters();
                searchView.clearFocus();
                toExit = false;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void toNewList(List<Characters> toSaveList, CharactersLoader toSaveLoader) {
        savedCharacters.addAll(toSaveList);
        savedCharactersLoader = toSaveLoader;
        toNewList();
    }

    private void toNewList() {
        clearList();
        initialiseLoader();
    }

    private void clearList() {
        adapter.clear();
        characters.clear();
    }

    @Override
    public void onBackPressed() {

        if (!isMainList) {
            clearList();
            charactersLoader = (CharactersLoader) savedCharactersLoader;
            characters.addAll(savedCharacters);
            adapter.setItems(characters);

            searchItem.collapseActionView();
            appBarLayout.setExpanded(true);

            if (!searchItem.isActionViewExpanded()) toExit = true;
        } else if (searchItem.isActionViewExpanded()) searchItem.collapseActionView();
    }

    @Override
    public boolean needToExit() {
        return toExit;
    }

    public interface SelectedCharacterIdListener {
        void onSelectedCharacterId(int id);
    }
}
