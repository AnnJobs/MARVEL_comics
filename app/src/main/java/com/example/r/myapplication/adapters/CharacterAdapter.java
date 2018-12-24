package com.example.r.myapplication.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.r.myapplication.Const;
import com.example.r.myapplication.R;
import com.example.r.myapplication.model.characters.Characters;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class CharacterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static List<Characters> charactersList = new ArrayList<>();
    private static final int ITEM_TYPE = 0;
    private static final int PROGRESS_TYPE = 1;
    private static Bitmap DEFAULT_ITEM_PICTURE;

    private static SelectedCharacterIdListener selectedCharacterIdListener;

    public CharacterAdapter(SelectedCharacterIdListener fragmentListener, Context context) {
        selectedCharacterIdListener = fragmentListener;
        DEFAULT_ITEM_PICTURE = BitmapFactory.decodeResource(context.getResources(), R.drawable.no_photo);
    }

    public void setItems(List<Characters> items) {
        int positionStart = charactersList.size();
        charactersList.addAll(items);
        notifyItemRangeChanged(positionStart, items.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new ProgressBarHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_bar, viewGroup, false));
        } else
            return new CharacterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_character, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof CharacterViewHolder) {
            ((CharacterViewHolder) viewHolder).bind(charactersList.get(position));
        } else {
            ((ProgressBarHolder) viewHolder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return charactersList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (charactersList.size() != position) {
            charactersList.get(position);
            return ITEM_TYPE;
        } else {
            return PROGRESS_TYPE;
        }
    }

    public void clear() {
        charactersList.clear();
        notifyDataSetChanged();
    }

    static class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewStr;
        private ImageView imageChar;
        private int id;

        Bitmap image;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStr = itemView.findViewById(R.id.item_text);
            imageChar = itemView.findViewById(R.id.character_image);

            itemView.setOnClickListener(this);
        }

        public void bind(Characters item) {

            id = item.charId;

            textViewStr.setText(item.name);

            imageChar.setImageResource(0);

            if (!item.isImageSet()) {
                if (item.charImage.imgPath.equals(Const.IMAGE_NOT_FOUND_PATH)) {
                    item.setImage(DEFAULT_ITEM_PICTURE);
                } else {
                    Picasso.get()
                            .load(item.charImage.imgPath + "." + item.charImage.extension)
                            .into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    charactersList.get(getAdapterPosition()).setImage(bitmap);
                                }

                                @Override
                                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            });

                    Log.i("aaa", "list position " + charactersList.indexOf(item) + " was downloaded on position " + getAdapterPosition());
                }
            }
            imageChar.setImageBitmap(item.getImage());

        }

        @Override
        public void onClick(View v) {
            selectedCharacterIdListener.onSelectedCharacterId(id);
        }
    }

    static class ProgressBarHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public ProgressBarHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.load_characters);
        }

    }

    public interface SelectedCharacterIdListener {
        void onSelectedCharacterId(int charId);
    }
}
