package com.example.r.myapplication.data;

import com.example.r.myapplication.model.characterInfo.CharacterInfoResponse;
import com.example.r.myapplication.model.characters.СharactersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelAPI {

    @GET("/v1/public/characters")
    Call<СharactersResponse> loadHeroes(
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/characters/{characterId}")
    Call<CharacterInfoResponse> loadCharacterInfo(
            @Path("characterId") int characterId
    );

    @GET("/v1/public/characters")
    Call<СharactersResponse> loadSearchedCharacters(
            @Query("nameStartsWith") String nameStart,
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
