package com.example.r.myapplication.model;

import static com.example.r.myapplication.loaders.listLoaders.ListLoader.CHARACTER_TYPE;
import static com.example.r.myapplication.loaders.listLoaders.ListLoader.COMICS_TYPE;
import static com.example.r.myapplication.loaders.listLoaders.ListLoader.EVENTS_TYPE;
import static com.example.r.myapplication.loaders.listLoaders.ListLoader.SERIES_TYPE;

public class LoadingObjectsManager {

    public LoadingObject.LoadingObjetctFactory getLoadingObjectFactory(int dataType){
        switch (dataType) {
            case CHARACTER_TYPE: return Characters.getLoadingObjetctFactory();
            case COMICS_TYPE: return Comics.getLoadingObjetctFactory();
            case EVENTS_TYPE: return Events.getLoadingObjetctFactory();
            case SERIES_TYPE: return Series.getLoadingObjetctFactory();
            default: return null;
        }
    }
}
