package com.example.r.myapplication;

import android.util.Log;

public class ItemCountLoadingDeterminant {

    private int offset;
    private int startLimit;
    private int limit;
    private int extraOffset;

    public ItemCountLoadingDeterminant(int startOffset) {
        startLimit = SpanCountDefinitor.getSpanCount() * 4;
        limit = SpanCountDefinitor.getSpanCount() * 3;
        if (startOffset == 0) {
            offset = startLimit;
            Log.d("offsets", "from start limit = " + offset);
        } else {
            offset = startOffset;
            Log.d("offsets", "from start offset = " + offset);
        }
    }

    public ItemCountLoadingDeterminant(int limit, int startOffset) {
        this(startOffset);
        startLimit = limit;
    }

    public int getStartLimit() {
        return startLimit;
    }

    public int getLimit() {
        if (isSpanCountChanged()) {

            int curSpanCount = SpanCountDefinitor.getSpanCount();
            int differenceBetweenSpans = offset % curSpanCount;

            limit = SpanCountDefinitor.getSpanCount() * 3;
            extraOffset = differenceBetweenSpans;

            return differenceBetweenSpans + limit;
        }
        extraOffset = 0;
        return limit;
    }

    public int checkOffset(){
        return offset;
    }

    public int getOffset() {
        Log.i("aaa", "offset1 = " + offset);
        offset += limit;
        offset += extraOffset;
        return offset - limit - extraOffset;
    }



    private boolean isSpanCountChanged() {
        return (limit / 5) != (SpanCountDefinitor.getSpanCount());
    }
}
