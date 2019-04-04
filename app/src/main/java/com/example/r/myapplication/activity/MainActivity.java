package com.example.r.myapplication.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.r.myapplication.Listeners.OnBackPressedListener;
import com.example.r.myapplication.R;
import com.example.r.myapplication.fragment.CharacterFragment;
import com.example.r.myapplication.fragment.ChosenListFragment;
import com.example.r.myapplication.fragment.ComicInfoFragment;
import com.example.r.myapplication.fragment.EventInfoFragment;
import com.example.r.myapplication.fragment.GeneralInfoFragment;
import com.example.r.myapplication.fragment.GeneralListFragment;
import com.example.r.myapplication.fragment.SeriesInfoLoader;
import com.example.r.myapplication.loaders.listLoaders.ListLoader;
import com.example.r.myapplication.model.LoadingObject;

public class MainActivity extends FragmentActivity implements GeneralListFragment.SelectedItemIdListener{

    private static final String TAG_FRAGMENT_LIST = "fragment_list";
    private static final String TAG_CHARACTER_INFO_FRAGMENT = "character_info_fragment";

    private FragmentManager fm;
    private Fragment fragment;

    private MainActivity thisActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        fragment = fm.findFragmentById(R.id.character_container);

        if (fragment == null) {
            fragment = GeneralListFragment.newInstance(ListLoader.CHARACTER_TYPE);
            fm.beginTransaction()
                    .addToBackStack(TAG_FRAGMENT_LIST)
                    .add(R.id.character_container, fragment)
                    .commit();
        }
    }

    @Override
    public <T extends LoadingObject> void onSelectedItemId(int id, int itemType) {
        this.<T>addInfoFragment(id, itemType);
    }

    private <T extends LoadingObject> void addInfoFragment(int id, final int itemType) {

        GeneralInfoFragment.ListSelectedListener listSelectedListener =
                new GeneralInfoFragment.ListSelectedListener() {
                    @Override
                    public void loadWholeList(int dataType, int id) {
                        fragment = ChosenListFragment.<T>newInstance(dataType, itemType, id);
                        Log.d("types", "loadWholeList: " + dataType);
                        fm.beginTransaction()
                                .replace(R.id.character_container, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                };

        GeneralInfoFragment fragment = null;

        Log.d("llllll", "addInfoFragment: itemType = " + itemType); //ошибка в передаче типа от Info к Info

        switch (itemType){
            case ListLoader.CHARACTER_TYPE:
                fragment = CharacterFragment.newInstance(id);
                break;
            case ListLoader.COMICS_TYPE:
                fragment = ComicInfoFragment.newInstance(id);
                break;
            case ListLoader.SERIES_TYPE:
                fragment = SeriesInfoLoader.newInstance(id);
                break;
            case ListLoader.EVENTS_TYPE:
                fragment = EventInfoFragment.newInstance(id);
                break;
        }
        if (fragment != null) {
            fragment.setListeners(listSelectedListener,
                    new GeneralListFragment.SelectedItemIdListener() {
                        @Override
                        public <T extends LoadingObject> void onSelectedItemId(int id, int itemType) {
                            thisActivity.<T>addInfoFragment(id, itemType);
                        }
                    });
        }
        fm.beginTransaction()
                .replace(R.id.character_container, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        int fragmentsInStack = fm.getBackStackEntryCount();
        Fragment curFragment = fm.getFragments().get(fm.getFragments().size() - 1);

        if (curFragment instanceof OnBackPressedListener && !((OnBackPressedListener) curFragment)
                .needToExit()) {
            ((OnBackPressedListener) curFragment).onBackPressed();
        } else {
            Log.i("FRAGMENTS", "count = " + fragmentsInStack);
            if (fragmentsInStack > 1) {
                getSupportFragmentManager().popBackStack();
            } else if (fragmentsInStack == 1) {
                finish();
            } else {
                super.onBackPressed();
            }
        }

    }
}