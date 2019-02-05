package com.example.r.myapplication.model;

public abstract class RepresentativeCover extends LoadingObject {
    public abstract String getTitle();

    @Override
    public String getImgAspectRatio() {
        return "standard_fantastic";
    }
}
