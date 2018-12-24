package com.example.r.myapplication.loaders;

import com.example.r.myapplication.ItemCountLoadingDeterminant;
import com.example.r.myapplication.data.MarvelService;
import com.example.r.myapplication.model.characters.Characters;
import com.example.r.myapplication.model.characters.СharactersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharactersLoader {

    public static boolean isLoading = false;

    private String searchedName;

    public MarvelService marvelService = new MarvelService();
    Listener listener;
    private ItemCountLoadingDeterminant itemCountLoadingDeterminant = new ItemCountLoadingDeterminant();

    public CharactersLoader(Listener listener) {
        this.listener = listener;
    }

    private void loadCharacters(int limit, int offset) {

        marvelService.getApi()
                .loadHeroes(
                        limit, offset
                ).enqueue(newCallback());

        isLoading = false;

    }

    //////////FOR SEARCH/////////
    private void loadCharacters(int limit, int offset, String nameStartsWith) {

        marvelService.getApi()
                .loadSearchedCharacters(
                        nameStartsWith, limit, offset
                ).enqueue(newCallback());

        isLoading = false;

    }

    public void loadMore() {
        if (searchedName == null) {
            loadCharacters(itemCountLoadingDeterminant.getLimit(), itemCountLoadingDeterminant.getOffset());
        } else
            loadCharacters(itemCountLoadingDeterminant.getLimit(), itemCountLoadingDeterminant.getOffset(), searchedName);

    }

    public void loadCharacters() {
        if (searchedName == null) {
            loadCharacters(itemCountLoadingDeterminant.getStartLimit(), 0);
        } else loadCharacters(itemCountLoadingDeterminant.getLimit(), 0, searchedName);
    }


    private Callback<СharactersResponse> newCallback() {
        return new Callback<СharactersResponse>() {
            @Override
            public void onResponse(Call<СharactersResponse> call, Response<СharactersResponse> response) {
                if (response.isSuccessful()) {
                    listener.setCharactersOnRecyclerView(response.body().data.characters);
                }
            }

            @Override
            public void onFailure(Call<СharactersResponse> call, Throwable t) {

            }
        };
    }

    public void setSearchedName(String name){
        searchedName = name;
    }


    public interface Listener {
        void setCharactersOnRecyclerView(List<Characters> list);
    }
}
