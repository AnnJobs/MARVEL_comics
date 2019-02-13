package com.example.r.myapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.r.myapplication.R;
import com.example.r.myapplication.model.LoadingObject;
import com.example.r.myapplication.model.Characters;
import com.example.r.myapplication.model.RepresentativeCover;

import java.util.ArrayList;
import java.util.List;

public class ListItemAdapter<T extends LoadingObject> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int limit;

    private List<T> itemList = new ArrayList<>();
    private static final int ITEM_TYPE = 0;
    private static final int PROGRESS_TYPE = 1;
    private static final int NEXT_BUTTON_TYPE = 2;

    private boolean isEnd = false;

    private int itemResource;


    private static SelectedItemIdListener selectedItemIdListener;
    private static NextButtonListener nextButtonListener;

    public ListItemAdapter(SelectedItemIdListener fragmentListener) {
        selectedItemIdListener = fragmentListener;
    }

    public ListItemAdapter(SelectedItemIdListener fragmentListener, NextButtonListener buttonListener, int limit) {
        this(fragmentListener);
        nextButtonListener = buttonListener;
        this.limit = limit;
    }

    public void setItems(List<T> items) {
        int positionStart = itemList.size();
        itemList.addAll(items);
        if (itemResource == 0 && itemList.size() != 0){
            itemResource = getItemsResource(itemList.get(0));
        }
        notifyItemRangeChanged(positionStart, items.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == ITEM_TYPE) {
            Log.d("adapter", "onCreateViewHolder: item");
            return new ItemViewHolder<T>(LayoutInflater.from(viewGroup.getContext()).inflate(itemResource, viewGroup, false));
        } else if (i == NEXT_BUTTON_TYPE) {
            Log.d("adasdaf", "getItemViewType: next1");
            return new NextViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_next, viewGroup, false));
        } else {
            Log.d("adapter", "onCreateViewHolder: progressBar");
            return new ProgressBarHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_bar, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Log.d("prb", "onBindViewHolder: " + isEnd);
        if (viewHolder instanceof ListItemAdapter.ItemViewHolder) {
            ItemViewHolder<T> itemViewHolder = (ItemViewHolder<T>) viewHolder;
            try {
                itemViewHolder.bind(itemList.get(position));
            } catch (NullPointerException e) {
                Log.d("ssssss", "onBindViewHolder: items = " + getItemCount());
            }
            Log.d("tttttt", "onBindViewHolder: item");
        } else if (viewHolder instanceof ListItemAdapter.NextViewHolder) {
            Log.d("tttttt", "onBindViewHolder: next");
        } else if (isEnd) {
            ((ProgressBarHolder) viewHolder).progressBar.setVisibility(View.GONE);
            ((ProgressBarHolder) viewHolder).item.getLayoutParams().width = 0;
            Log.d("tttttt", "onBindViewHolder: pb gone");
        } else {
            Log.d("tttttt", "onBindViewHolder: pb visible");
            if (((ProgressBarHolder) viewHolder).progressBar.getVisibility() == View.GONE) {
                ((ProgressBarHolder) viewHolder).progressBar.setVisibility(View.VISIBLE);
                ((ProgressBarHolder) viewHolder).item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if ((limit != 0) && (limit == position)) {
            Log.d("adasdaf", "getItemViewType: next1");
            return NEXT_BUTTON_TYPE;
        }
        if (itemList.size() != position) {
            return ITEM_TYPE;
        } else
            return PROGRESS_TYPE;
    }

    public void clear() {
        itemList.clear();
        notifyDataSetChanged();
        isEnd = false;
        Log.d("prb", "clear: " + isEnd);
    }

    static class ItemViewHolder<T extends LoadingObject> extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewStr;
        private ImageView itemImage;
        private int id;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStr = itemView.findViewById(R.id.item_text);
            itemImage = itemView.findViewById(R.id.item_image);

            itemView.setOnClickListener(this);
        }


        void bind(T item) {

            String text = "";
            if (item instanceof RepresentativeCover) {
                text = ((RepresentativeCover) item).getTitle();

                Log.d("text", text);
            } else if (item instanceof Characters) {
                text = ((Characters) item).name;
            }

            id = item.id;

            textViewStr.setText(text);

            if (item.image != null){
                item.image.downloadImg(item.getImgAspectRatio(), itemImage);
            }
        }

        @Override
        public void onClick(View v) {
            selectedItemIdListener.onSelectedItemId(id);
        }
    }

    static class ProgressBarHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        View item;

        ProgressBarHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.load_characters);
            item = view;
        }
    }

    static class NextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        NextViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.next_button).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            nextButtonListener.onNextButtonPressed();
        }
    }

    private int getItemsResource(T item){
        if (item instanceof RepresentativeCover){
            Log.d("aaaaaaaaa", "getItemsResource: yes ");
            return R.layout.item_cover;
        } else return R.layout.item_character;
    }

    public void listEnded() {
        isEnd = true;
        Log.d("prb", "listEnded: " + isEnd);
    }

    public interface SelectedItemIdListener {
        void onSelectedItemId(int charId);
    }

    public interface NextButtonListener {
        void onNextButtonPressed();
    }
}