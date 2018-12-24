package com.example.r.myapplication;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class SpanCountDefinitor {

    private static int spanCount;

    public static void determineSpanCount(Context context){

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        Log.i("SPAN_COUNT", "width = " + width);
        if (width < 300) spanCount = 1;
        else if (width < 650) spanCount = 2;
        else if (width < 1200) spanCount = 4;
        else spanCount = 5;
    }

    public static int getSpanCount(){
        return spanCount;
    }

}
