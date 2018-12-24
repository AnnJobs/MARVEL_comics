package com.example.r.myapplication;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class Decorator extends RecyclerView.ItemDecoration {

//    private static final int LEFT_ITEM = 1;
//    private static final int RIGHT_ITEM = 2;
//    private static final int USUAL_ITEM = 0;

    private int offset;

    public Decorator(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

//        int positionInSpan = getPositionInSpan(parent.getChildAdapterPosition(view));
//
//        if (positionInSpan == RIGHT_ITEM) {
//            outRect.set(offset, 0, offset * 2, offset * 2);
//        } else if (positionInSpan == LEFT_ITEM) {
//            outRect.set(offset * 2, 0, offset, offset * 2);
//        } else outRect.set(offset, 0, offset, offset * 2);

        outRect.set(offset, 0, offset, offset * 2);
    }

//    private int getPositionInSpan(int index) {
//        if (index % SpanCountDefinitor.getSpanCount() == 0) {
//            return LEFT_ITEM;
//        } else if (index % (SpanCountDefinitor.getSpanCount() - 1) == 0) {
//            return RIGHT_ITEM;
//        } else return USUAL_ITEM;
//    }
}
