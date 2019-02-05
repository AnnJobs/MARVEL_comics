package com.example.r.myapplication;

import android.content.Context;

public class UnitConverter {

    public static float pxToDp(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float dpToPx(Context context, float dp){
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
