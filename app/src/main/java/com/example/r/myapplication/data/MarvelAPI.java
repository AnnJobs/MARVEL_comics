package com.example.r.myapplication.data;

import com.example.r.myapplication.model.Characters;
import com.example.r.myapplication.model.Comics;
import com.example.r.myapplication.model.Events;
import com.example.r.myapplication.model.ListResponse;
import com.example.r.myapplication.model.Series;
import com.example.r.myapplication.model.Stories;
import com.example.r.myapplication.model.characterInfo.CharacterInfoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelAPI {

    @GET("/v1/public/characters")
    Call<ListResponse<Characters>> loadHeroes(
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/characters")
    Call<ListResponse<Characters>> loadSearchedCharacters(
            @Query("nameStartsWith") String nameStart,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/characters/{characterId}")
    Call<CharacterInfoResponse> loadCharacterInfo(
            @Path("characterId") int characterId
    );

    @GET("/v1/public/characters/{characterId}/comics")
    Call<ListResponse<Comics>> loadComics(
            @Path("characterId") int characterId,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/comics")
    Call<ListResponse<Comics>> loadComics(
            @Query("titleStartsWith") String titleStartsWith,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/comics")
    Call<ListResponse<Comics>> loadComics(
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/characters/{characterId}/series")
    Call<ListResponse<Series>> loadSeries(
            @Path("characterId") int characterId,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/series")
    Call<ListResponse<Series>> loadSeries(
            @Query("titleStartsWith") String titleStartsWith,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/series")
    Call<ListResponse<Series>> loadSeries(
            @Query("limit") int limit,
            @Query("offset") int offset
    );

//    @GET("/v1/public/characters/{characterId}/stories")
//    Call<ListResponse<Stories>> loadStories(
//            @Path("characterId") int characterId,
//            @Query("limit") int limit,
//            @Query("offset") int offset
//    );
//
//    @GET("/v1/public/stories")
//    Call<ListResponse<Stories>> loadStories(
//            @Query("titleStartsWith") String titleStartsWith,
//            @Query("limit") int limit,
//            @Query("offset") int offset
//    );
//
//    @GET("/v1/public/stories")
//    Call<ListResponse<Stories>> loadStories(
//            @Query("limit") int limit,
//            @Query("offset") int offset
//    );

    @GET("/v1/public/characters/{characterId}/events")
    Call<ListResponse<Events>> loadEvents(
            @Path("characterId") int characterId,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/events")
    Call<ListResponse<Events>> loadEvents(
            @Query("titleStartsWith") String titleStartsWith,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/events")
    Call<ListResponse<Events>> loadEvents(
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
