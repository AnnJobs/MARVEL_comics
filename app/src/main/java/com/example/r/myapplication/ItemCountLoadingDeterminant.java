package com.example.r.myapplication;

import android.util.Log;

public class ItemCountLoadingDeterminant {

    private int offset;
    private int startLimit;
    private int limit;
    private int extraOffset;

    public ItemCountLoadingDeterminant() {
        startLimit = SpanCountDefinitor.getSpanCount() * 8;
        limit = SpanCountDefinitor.getSpanCount() * 5;
        offset = startLimit;
    }

    public int getStartLimit() {
        return startLimit;
    }

    public int getLimit() {
        int curSpanCount = SpanCountDefinitor.getSpanCount();
        if (isSpanCountChanged()) {
            int differenceBetweenSpans = offset % curSpanCount;

            limit = SpanCountDefinitor.getSpanCount() * 5;
            extraOffset = differenceBetweenSpans;

            return differenceBetweenSpans + limit;
        }
        extraOffset = 0;
        limit = SpanCountDefinitor.getSpanCount() * 5;
        return limit;
    }

    public int getOffset() {
        offset += limit;
        offset += extraOffset;
        Log.i("aaa", "offset = " + offset);
        return offset - limit - extraOffset;
    }

    private boolean isSpanCountChanged() {
        return (limit / 5) != (SpanCountDefinitor.getSpanCount());
    }
}
