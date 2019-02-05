package com.example.r.myapplication.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.example.r.myapplication.Listeners.OnBackPressedListener;
import com.example.r.myapplication.R;
import com.example.r.myapplication.fragment.CharacterFragment;
import com.example.r.myapplication.fragment.MainListFragment;
import com.example.r.myapplication.loaders.listLoaders.ListLoader;

public class MainActivity extends FragmentActivity implements MainListFragment.SelectedCharacterIdListener {

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
            charFragment = MainListFragment.newInstance(ListLoader.CHARACTER_TYPE);
            fm.beginTransaction()
                    .addToBackStack(TAG_FRAGMENT_LIST)
                    .add(R.id.character_container, charFragment)
                    .commit();
        }
    }

    @Override
    public void onSelectedCharacterId(int id) {
        onIdWasReceived(id);
    }

    private void onIdWasReceived(int id) {
        addCharacterInfoFragment(id);
    }

    private void addCharacterInfoFragment(int id) {
        Fragment fragment = CharacterFragment.newInstance(id);
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