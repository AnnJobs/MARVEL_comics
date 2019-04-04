package com.example.r.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.r.myapplication.Const;
import com.example.r.myapplication.Decorator;
import com.example.r.myapplication.ItemCountLoadingDeterminant;
import com.example.r.myapplication.R;
import com.example.r.myapplication.SpanCountDefinitor;
import com.example.r.myapplication.adapters.ListItemAdapter;
import com.example.r.myapplication.loaders.InfoLoader;
import com.example.r.myapplication.loaders.listLoaders.ListLoader;
import com.example.r.myapplication.model.LoadingObject;
import com.example.r.myapplication.model.characterInfo.Info;
import com.example.r.myapplication.model.lists.Characters;
import com.example.r.myapplication.model.lists.Comics;
import com.example.r.myapplication.model.lists.Events;
import com.example.r.myapplication.model.lists.Series;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public abstract class GeneralInfoFragment extends Fragment {

    protected static final String ARG_ITEM_ID = "item_id";
    protected static final String ARG_ITEM_TYPE = "item_type";

    private ListItemAdapter<Comics> comicsAdapter;
    private ListLoader<Comics> comicsLoader;

    private ListLoader<Series> seriesLoader;
    private ListItemAdapter<Series> seriesAdapter;

    private ListLoader<Events> eventsLoader;
    private ListItemAdapter<Events> eventsAdapter;

    private ListItemAdapter<Characters> charactersAdapter;
    private ListLoader<Characters> charactersLoader;

    private ImageView itemImage;
    private TextView itemName;
    private TextView itemDescription;
    private InfoLoader infoLoader;

    protected ArrayList<RecyclerView> listOfRecyclerView = new ArrayList<>();

    private ScrollView scrollView;
    private LinearLayout progressBarLayout;
    private Toolbar toolbar;

    private static int curId;
    private int itemType;

    private GeneralInfoFragment.ListSelectedListener listSelectedListener;
    private GeneralListFragment.SelectedItemIdListener selectedItemIdListener;

    public void setListeners(GeneralInfoFragment.ListSelectedListener listSelectedListener,
                             GeneralListFragment.SelectedItemIdListener selectedItemIdListener) {
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
        Log.d("yyyy", "onViewCreated: called");
        Bundle args = getArguments();
        if (!(args == null) && args.containsKey(ARG_ITEM_ID)) {
            itemType = args.getInt(ARG_ITEM_TYPE);
            curId = args.getInt(ARG_ITEM_ID);
        }

        Log.d("llllll", "onViewCreated: itemType" + itemType);

        if (isNewInstance()) {

            initialiseViews(view);
            buildToolbar();

            Log.d("wwww", "onViewCreated: " + isNewInstance());
            if (savedInstanceState == null ||
                    savedInstanceState.getString("CHARACTER_NAME") == null ||
                    savedInstanceState.getParcelable("CHARACTER_IMAGE") == null) {

                initInfoLoader();
                infoLoader.loadInfo(curId);

                initRecyclerView(view);

                loadLists();

            } else {
                if (scrollView.getVisibility() == View.GONE) {
                    progressBarLayout.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public abstract void makeAdapters();

    public abstract void loadLists();

    public abstract void findRV(View view);

    public abstract void makeLoaders(View view);

    private boolean hasItemNotLoaded() {
        return itemImage.getDrawable() == null;
    }

    private boolean isNewInstance() {
        return toolbar == null;
    }

    private void initInfoLoader() {
        if (infoLoader == null) {
            infoLoader = new InfoLoader(itemType, new InfoLoader.Listener() {
                @Override
                public void addCharacterInfoIntoFragment(Info characterInfo) {
                    if (scrollView.getVisibility() == View.GONE) {
                        progressBarLayout.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                    bindData(characterInfo);
                }
            });
        }
    }

    private void bindData(Info characterInfo) {
        if (Const.IMAGE_NOT_FOUND_PATH.equals(characterInfo.charImage.imgPath)) {
            itemImage.setImageResource(R.drawable.no_photo);
        } else {
            Picasso.get().load(characterInfo.charImage.imgPath + "."
                    + characterInfo.charImage.extension).into(itemImage);
        }
        itemName.setText(characterInfo.name);
        if (characterInfo.description == null || characterInfo.description.equals("")) {
            itemDescription.setText(getString(R.string.no_description));
        } else {
            itemDescription.setText(characterInfo.description);
        }
    }

    private void initialiseViews(View view) {
        if (isNewInstance()) {
            itemImage = view.findViewById(R.id.character_info_image);
            itemName = view.findViewById(R.id.character_info_name);
            itemDescription = view.findViewById(R.id.character_info_description);
            scrollView = view.findViewById(R.id.char_info_scroll_view);
            progressBarLayout = view.findViewById(R.id.progress_bar_info);
            toolbar = view.findViewById(R.id.toolbar_for_character_info);

            findRV(view);
            Log.d("recycler", "initialiseViews: ");
        }
    }

    private void initRecyclerView(final View view) {
        SpanCountDefinitor.determineSpanCount(getContext());

        makeLists(view);

        Decorator decorator = new Decorator(4);

        for (int i = 0; i < listOfRecyclerView.size(); i++) {
            listOfRecyclerView.get(i).addItemDecoration(decorator);
        }
    }

    private void deleteLists(){

    }

    protected <T extends LoadingObject> ListItemAdapter<T> newAdapter(final int dataType) {
        return new ListItemAdapter<>(new ListItemAdapter.SelectedItemIdListener() {
            @Override
            public void onSelectedItemId(int id) {
                selectedItemIdListener.onSelectedItemId(id, dataType);
            }
        }, new ListItemAdapter.NextButtonListener() {
            @Override
            public void onNextButtonPressed() {
                if (listSelectedListener != null) {
                    listSelectedListener.loadWholeList(dataType, curId);
                } else Log.d("Exceptions", "CharacterFragment: Series Next listener is null");
            }
        }, SpanCountDefinitor.getSpanCount() * 2);
    }

    protected <T extends LoadingObject> ListLoader<T> newListLoader(int listDataType, final View view, final RecyclerView rv) {
        final ListItemAdapter<T> adapter = (ListItemAdapter<T>)rv.getAdapter();

        return new ListLoader<>(new ListLoader.Listener<T>() {
            @Override
            public void setData(List<T> list, boolean isEnd) {
                if (list.size() == 0 && rv != null) {
//                    rv.setVisibility(View.GONE);
//                    view.findViewById().setVisibility(View.VISIBLE);
                } else {
                    if (adapter != null) {
                        adapter.setItems(list);
                        if (isEnd) adapter.listEnded();
                    }
                }
            }
        }, new ItemCountLoadingDeterminant(SpanCountDefinitor.getSpanCount() * 2,
                0), itemType, curId, listDataType);
    }

    private void setLayoutManager() {
        for (int i = 0; i < listOfRecyclerView.size(); i++) {
            listOfRecyclerView.get(i).setLayoutManager(new LinearLayoutManager(getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
        }
    }

    private void buildToolbar() {
        toolbar.setNavigationIcon(R.drawable.back_red);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getActivity().onBackPressed();
                } catch (NullPointerException ex) {
                    Log.e(TAG, "onClick: NullPointerException with onBackPressed");
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (scrollView.getVisibility() == View.VISIBLE || !hasItemNotLoaded()) {
            outState.putString("CHARACTER_NAME", itemName.getText().toString());
            outState.putString("CHARACTER_DESCRIPTION", itemDescription.getText().toString());

        }
        Log.d("eeeeee", "onSaveInstanceState: ");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            itemName.setText(savedInstanceState.getString("CHARACTER_NAME"));
            itemDescription.setText(savedInstanceState
                    .getString("CHARACTER_DESCRIPTION"));

        }
        Log.d("eeeeee", "onViewStateRestored: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        infoLoader.cancelCall();
    }

    private void makeLists(View view) {
        makeAdapters();
        makeLoaders(view);
        setLayoutManager();
    }

    public interface ListSelectedListener {
        void loadWholeList(int dataType, int id);
    }
}