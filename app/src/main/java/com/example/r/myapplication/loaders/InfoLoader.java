package com.example.r.myapplication.loaders;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.r.myapplication.data.MarvelService;
import com.example.r.myapplication.model.LoadingObject;
import com.example.r.myapplication.model.characterInfo.CharacterInfo;
import com.example.r.myapplication.model.characterInfo.CharacterInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoLoader {
    private MarvelService marvelService = new MarvelService();
    private Listener listener;

    public InfoLoader(Listener listener) {
        this.listener = listener;
    }

    public void loadInfo(int id) {
        marvelService.getApi().loadCharacterInfo(id).enqueue(new Callback<CharacterInfoResponse>() {
            @Override
            public void onResponse(@NonNull Call<CharacterInfoResponse> call, @NonNull Response<CharacterInfoResponse> response) {
                  if (response.isSuccessful()) {
                    Log.i("CONNECTION", "code = " + response.body().code);
                    listener.addCharacterInfoIntoFragment(response.body().data.information.get(0));
                  }
            }

            @Override
            public void onFailure(@NonNull Call<CharacterInfoResponse> call, @NonNull Throwable t) {
                Log.i("CONNECTION", "failed " + t.toString());
            }
        });
    }

    public interface Listener {
        void addCharacterInfoIntoFragment(CharacterInfo characterInfo);
    }

}
