package com.example.r.myapplication;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;

public class CharacterGridLayoutManager extends GridLayoutManager {

    ItemTypeListener listener;

    public CharacterGridLayoutManager(Context context, int spanCount, ItemTypeListener listener){
        super(context, spanCount);
        this.listener = listener;
    }

    public void setSpan(){
        setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int i) {
                if (listener.getItemType(i) == 0) {
                    return 1;
                } else return getSpanCount();
            }
        });

    }

    public interface ItemTypeListener {
        int getItemType(int i);
    }
}
