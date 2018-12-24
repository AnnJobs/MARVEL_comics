package com.example.r.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.r.myapplication.Const;
import com.example.r.myapplication.loaders.CharacterInfoLoader;
import com.example.r.myapplication.R;
import com.example.r.myapplication.model.characterInfo.CharacterInfo;
import com.squareup.picasso.Picasso;

public class CharacterFragment extends Fragment {

    private static final String ARG_CHARACTER_ID = "character_id";

    private ImageView characterImage;
    private TextView characterName;
    private TextView characterDescription;

    private static int curId;

    public static CharacterFragment newInstance(int charId) {
        Bundle args = new Bundle();
        args.putInt(ARG_CHARACTER_ID, charId);

        CharacterFragment characterFragment = new CharacterFragment();
        characterFragment.setArguments(args);
        return characterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_character, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (!(args==null) && args.containsKey(ARG_CHARACTER_ID)){
            curId = args.getInt(ARG_CHARACTER_ID);
        }

        initialiseViews(view);

        CharacterInfoLoader loader = new CharacterInfoLoader(new CharacterInfoLoader.Listener() {
            @Override
            public void addCharacterInfoIntoFragment(CharacterInfo characterInfo) {
                bindData(characterInfo);

            }
        });

        loader.loadInfo(curId);
    }

    private void bindData(CharacterInfo characterInfo){
        if (Const.IMAGE_NOT_FOUND_PATH.equals(characterInfo.charImage.imgPath)){
            characterImage.setImageResource(R.drawable.no_photo);
        } else {
            Picasso.get().load(characterInfo.charImage.imgPath + "." + characterInfo.charImage.extension).into(characterImage);
        }
        characterName.setText(characterInfo.name);
        if (characterInfo.description.equals("")){
            characterDescription.setText("No Description");
        } else {
            characterDescription.setText(characterInfo.description);
        }
    }

    private void initialiseViews(View view){
        characterImage = view.findViewById(R.id.character_info_image);
        characterName = view.findViewById(R.id.character_info_name);
        characterDescription = view.findViewById(R.id.character_info_description);
    }
}
