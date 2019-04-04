package com.example.r.myapplication.loaders;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.r.myapplication.data.MarvelService;
import com.example.r.myapplication.model.LoadingObject;
import com.example.r.myapplication.model.LoadingObjectsManager;
import com.example.r.myapplication.model.characterInfo.CharacterInfo;
import com.example.r.myapplication.model.characterInfo.CharacterInfoResponse;
import com.example.r.myapplication.model.characterInfo.Info;
import com.example.r.myapplication.model.characterInfo.InfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoLoader {
    private MarvelService marvelService = new MarvelService();
    private Listener listener;
    private int itemType;

    private Call<InfoResponse> call;
    private LoadingObjectsManager loadingObjectsManager = new LoadingObjectsManager();

    public InfoLoader(int itemType, Listener listener) {
        this.itemType = itemType;
        this.listener = listener;
    }

    public void loadInfo(int id) {
        call = loadingObjectsManager.getLoadingObjectFactory(itemType).loadInfo(id, marvelService.getApi());

        call.enqueue(new Callback<InfoResponse>() {
            @Override
            public void onResponse(@NonNull Call<InfoResponse> call, @NonNull Response<InfoResponse> response) {
                  if (response.isSuccessful()) {
                    listener.addCharacterInfoIntoFragment(response.body().data.information.get(0));
                  }
            }

            @Override
            public void onFailure(@NonNull Call<InfoResponse> call, @NonNull Throwable t) {
                Log.i("CONNECTION", "failed " + t.toString());
            }
        });
    }

    public void cancelCall(){
        call.cancel();
    }

    public interface Listener {
        void addCharacterInfoIntoFragment(Info characterInfo);
    }

}
