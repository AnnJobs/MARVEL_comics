package com.example.r.myapplication.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.example.r.myapplication.Listeners.OnBackPressedListener;
import com.example.r.myapplication.R;
import com.example.r.myapplication.fragment.CharacterFragment;
import com.example.r.myapplication.fragment.ChosenListFragment;
import com.example.r.myapplication.fragment.GeneralListFragment;
import com.example.r.myapplication.loaders.listLoaders.ListLoader;
import com.example.r.myapplication.model.Characters;
import com.example.r.myapplication.model.Comics;

public class MainActivity extends FragmentActivity implements GeneralListFragment.SelectedItemIdListener{

    private static final String TAG_FRAGMENT_LIST = "fragment_list";
    private static final String TAG_CHARACTER_INFO_FRAGMENT = "character_info_fragment";

    private FragmentManager fm;
    private Fragment charFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        charFragment = fm.findFragmentById(R.id.character_container);

        if (charFragment == null) {
            charFragment = GeneralListFragment.newInstance(ListLoader.CHARACTER_TYPE);
            fm.beginTransaction()
                    .addToBackStack(TAG_FRAGMENT_LIST)
                    .add(R.id.character_container, charFragment)
                    .commit();
        }
    }

    @Override
    public void onSelectedItemId(int id) {
        onIdWasReceived(id);
    }

    private void onIdWasReceived(int id) {
        addCharacterInfoFragment(id);
    }

    private void addCharacterInfoFragment(int id) {
        Fragment fragment = CharacterFragment.newInstance(id);
        ((CharacterFragment) fragment).setListeners(new CharacterFragment.ListSelectedListener() {
            @Override
            public void loadWholeList(int dataType, int id) {
                ChosenListFragment<Comics> frag = ChosenListFragment.newInstance(dataType, id);
                fm.beginTransaction()
                        .addToBackStack(TAG_FRAGMENT_LIST)
                        .add(R.id.character_container, frag)
                        .commit();
            }
        }, new GeneralListFragment.SelectedItemIdListener() {
            @Override
            public void onSelectedItemId(int id) {

            }
        });
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

        if (curFragment instanceof OnBackPressedListener && !((OnBackPressedListener) curFragment).needToExit()) {
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