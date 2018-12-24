package com.example.r.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class HeaderBehavior extends CoordinatorLayout.Behavior<Toolbar> {

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull Toolbar child, @NonNull View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull Toolbar child, @NonNull View dependency) {

        float change = Math.max(0, ((RecyclerView)dependency).getLayoutManager().getChildAt(0).getTranslationY());
        child.setScaleY((float) (child.getLayoutParams().height/(child.getLayoutParams().height - change)));
        Log.i("HEADER_CHANGE", "changed!!!!!!!!!!!");
        return true;
    }
}
