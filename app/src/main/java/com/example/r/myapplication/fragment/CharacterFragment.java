package com.example.r.myapplication.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.r.myapplication.Const;
import com.example.r.myapplication.Decorator;
import com.example.r.myapplication.ItemCountLoadingDeterminant;
import com.example.r.myapplication.SpanCountDefinitor;
import com.example.r.myapplication.adapters.ListItemAdapter;
import com.example.r.myapplication.loaders.InfoLoader;
import com.example.r.myapplication.R;
import com.example.r.myapplication.loaders.listLoaders.ListLoader;
import com.example.r.myapplication.model.Events;
import com.example.r.myapplication.model.Series;
import com.example.r.myapplication.model.characterInfo.CharacterInfo;
import com.example.r.myapplication.model.Comics;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterFragment extends Fragment {

    private static final String ARG_CHARACTER_ID = "character_id";

    private ImageView characterImage;
    private TextView characterName;
    private TextView characterDescription;

    private ScrollView scrollView;
    private LinearLayout progressBarLayout;
    private Toolbar toolbar;

    private RecyclerView recyclerViewComics;
    private ListItemAdapter<Comics> comicsAdapter;
    private ListLoader<Comics> comicsLoader;

    private ListLoader<Series> seriesLoader;
    private ListItemAdapter<Series> seriesAdapter;
    private RecyclerView recyclerViewSeries;

    private ListLoader<Events> eventsLoader;
    private ListItemAdapter<Events> eventsAdapter;
    private RecyclerView recyclerViewEvents;

    private static int curId;

    private ListSelectedListener listSelectedListener;
    private GeneralListFragment.SelectedItemIdListener selectedItemIdListener;


    public static CharacterFragment newInstance(int charId) {
        Bundle args = new Bundle();
        args.putInt(ARG_CHARACTER_ID, charId);

        CharacterFragment characterFragment = new CharacterFragment();
        characterFragment.setArguments(args);
        return characterFragment;
    }

    public void setListeners(ListSelectedListener listSelectedListener,
                             GeneralListFragment.SelectedItemIdListener selectedItemIdListener){
        this.selectedItemIdListener = selectedItemIdListener;
        this.listSelectedListener = listSelectedListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_character, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (!(args == null) && args.containsKey(ARG_CHARACTER_ID)) {
            curId = args.getInt(ARG_CHARACTER_ID);
        }

        initialiseViews(view);
        buildToolbar();

        if (savedInstanceState == null ||
                savedInstanceState.getString("CHARACTER_NAME") == null ||
                savedInstanceState.getParcelable("CHARACTER_IMAGE") == null) {

            InfoLoader loader = new InfoLoader(new InfoLoader.Listener() {
                @Override
                public void addCharacterInfoIntoFragment(CharacterInfo characterInfo) {
                    if (scrollView.getVisibility() == View.GONE) {
                        progressBarLayout.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                    bindData(characterInfo);

                }
            });

            loader.loadInfo(curId);
        } else {
            if (scrollView.getVisibility() == View.GONE) {
                progressBarLayout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        }

        initRecyclerView(view);

        comicsLoader.loadItems();
        seriesLoader.loadItems();
        eventsLoader.loadItems();
    }

    private boolean hasItemNotLoaded() {
        return characterImage.getDrawable() == null;
    }

    private void bindData(CharacterInfo characterInfo) {
        if (Const.IMAGE_NOT_FOUND_PATH.equals(characterInfo.charImage.imgPath)) {
            characterImage.setImageResource(R.drawable.no_photo);
        } else {
            Picasso.get().load(characterInfo.charImage.imgPath + "."
                    + characterInfo.charImage.extension).into(characterImage);
        }
        characterName.setText(characterInfo.name);
        if (characterInfo.description.equals("")) {
            characterDescription.setText(getString(R.string.no_description));
        } else {
            characterDescription.setText(characterInfo.description);
        }
    }

    private void initialiseViews(View view) {
        characterImage = view.findViewById(R.id.character_info_image);
        characterName = view.findViewById(R.id.character_info_name);
        characterDescription = view.findViewById(R.id.character_info_description);
        scrollView = view.findViewById(R.id.char_info_scroll_view);
        progressBarLayout = view.findViewById(R.id.progress_bar_info);
        toolbar = view.findViewById(R.id.toolbar_for_character_info);

        recyclerViewComics = view.findViewById(R.id.comics_recycler_view);
        recyclerViewSeries = view.findViewById(R.id.series_recycler_view);
        recyclerViewEvents = view.findViewById(R.id.events_recycler_view);
        Log.d("recycler", "initialiseViews: ");
    }

    private void initRecyclerView(final View view) {
        SpanCountDefinitor.determineSpanCount(getContext());

        initComicsRV(view);
        initSeriesRV(view);
        initEventsRV(view);

        Decorator decorator = new Decorator(4);
        recyclerViewComics.addItemDecoration(decorator);
        recyclerViewSeries.addItemDecoration(decorator);
        recyclerViewEvents.addItemDecoration(decorator);
    }

    private void initSeriesRV(final View view) {

        seriesAdapter = new ListItemAdapter<>(new ListItemAdapter.SelectedItemIdListener() {
            @Override
            public void onSelectedItemId(int charId) {

            }
        }, new ListItemAdapter.NextButtonListener() {
            @Override
            public void onNextButtonPressed() {
                listSelectedListener.loadWholeList(ListLoader.SERIES_TYPE, curId);
            }
        }, SpanCountDefinitor.getSpanCount() * 2);


        seriesLoader = new ListLoader<>(new ListLoader.Listener<Series>() {
            @Override
            public void setData(List<Series> list, boolean isEnd) {
                if (list.size() == 0 && recyclerViewSeries != null) {
                    recyclerViewSeries.setVisibility(View.GONE);
                    view.findViewById(R.id.no_series_text_view).setVisibility(View.VISIBLE);
                } else {
                    if (seriesAdapter != null) {
                        seriesAdapter.setItems(list);
                        if (isEnd) seriesAdapter.listEnded();
                    }
                }
            }
        }, new ItemCountLoadingDeterminant(SpanCountDefinitor.getSpanCount() * 2,
                0), curId, ListLoader.SERIES_TYPE);

        recyclerViewSeries.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSeries.setAdapter(seriesAdapter);
    }

    private void initComicsRV(final View view) {

        comicsAdapter = new ListItemAdapter<>(new ListItemAdapter.SelectedItemIdListener() {
            @Override
            public void onSelectedItemId(int charId) {
            }
        }, new ListItemAdapter.NextButtonListener() {
            @Override
            public void onNextButtonPressed() {
                listSelectedListener.loadWholeList(ListLoader.COMICS_TYPE, curId);
            }
        },SpanCountDefinitor.getSpanCount() * 2);

        comicsLoader = new ListLoader<>(new ListLoader.Listener<Comics>() {
            @Override
            public void setData(List<Comics> list, boolean isEnd) {
                if (list.size() == 0 && recyclerViewComics != null) {
                    Log.d("recycler", "rv gone ");
                    recyclerViewComics.setVisibility(View.GONE);
                    view.findViewById(R.id.no_comics_text_view).setVisibility(View.VISIBLE);
                } else {
                    if (comicsAdapter != null) {
                        comicsAdapter.setItems(list);
                        if (isEnd) comicsAdapter.listEnded();
                    }
                }
            }
        }, new ItemCountLoadingDeterminant(SpanCountDefinitor.getSpanCount() * 2, 0)
                , curId, ListLoader.COMICS_TYPE);

        recyclerViewComics.setAdapter(comicsAdapter);
        recyclerViewComics.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

    }

    private void initEventsRV(final View view) {
        eventsAdapter = new ListItemAdapter<>(new ListItemAdapter.SelectedItemIdListener() {
            @Override
            public void onSelectedItemId(int charId) {
            }
        }, new ListItemAdapter.NextButtonListener() {
            @Override
            public void onNextButtonPressed() {
                listSelectedListener.loadWholeList(ListLoader.EVENTS_TYPE, curId);
            }
        },SpanCountDefinitor.getSpanCount() * 2);

        eventsLoader = new ListLoader<>(new ListLoader.Listener<Events>() {
            @Override
            public void setData(List<Events> list, boolean isEnd) {
                if (list.size() == 0 && recyclerViewEvents != null) {
                    recyclerViewEvents.setVisibility(View.GONE);
                    view.findViewById(R.id.no_events_text_view).setVisibility(View.VISIBLE);
                } else {
                    if (eventsAdapter != null) {
                        eventsAdapter.setItems(list);
                        if (isEnd) eventsAdapter.listEnded();
                    }
                }
            }
        }, new ItemCountLoadingDeterminant(SpanCountDefinitor.getSpanCount() * 2,
                0), curId, ListLoader.EVENTS_TYPE);

        recyclerViewEvents.setAdapter(eventsAdapter);
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));

    }

    private void buildToolbar() {
        try {

            toolbar.setNavigationIcon(R.drawable.back_red);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
        } catch (NullPointerException e) {
            Log.e("CharacterFragment", "NullPointerException in onBackPressed");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (scrollView.getVisibility() == View.VISIBLE || !hasItemNotLoaded()) {
            outState.putString("CHARACTER_NAME", characterName.getText().toString());
            outState.putString("CHARACTER_DESCRIPTION", characterDescription.getText().toString());
            outState.putParcelable("CHARACTER_IMAGE", ((BitmapDrawable) characterImage
                    .getDrawable()).getBitmap());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            characterName.setText(savedInstanceState.getString("CHARACTER_NAME"));
            characterDescription.setText(savedInstanceState
                    .getString("CHARACTER_DESCRIPTION"));
            characterImage.setImageBitmap((Bitmap) savedInstanceState
                    .getParcelable("CHARACTER_IMAGE"));

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recyclerViewComics = null;
        comicsAdapter = null;
        comicsLoader = null;
    }

    public interface ListSelectedListener {
        void loadWholeList(int dataType, int id);
    }

    @Override
    public void onPause() {
        super.onPause();
        
    }
}
