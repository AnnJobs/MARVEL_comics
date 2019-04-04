package com.example.r.myapplication.data;

import com.example.r.myapplication.model.characterInfo.InfoResponse;
import com.example.r.myapplication.model.lists.Characters;
import com.example.r.myapplication.model.lists.Comics;
import com.example.r.myapplication.model.lists.Creators;
import com.example.r.myapplication.model.lists.Events;
import com.example.r.myapplication.model.lists.ListResponse;
import com.example.r.myapplication.model.lists.Series;
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
    Call<InfoResponse> loadCharacterInfo(
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

    @GET("/v1/public/comics/{comicId}")
    Call<InfoResponse> loadComicInfo(@Path("comicId") int id);

    @GET("/v1/public/series/{seriesId}")
    Call<InfoResponse> loadSeriesInfo(@Path("seriesId") int id);

    @GET("/v1/public/events/{eventId}")
    Call<InfoResponse> loadEventInfo(@Path("eventId") int id);

    @GET("/v1/public/comics/{comicId}/characters")
    Call<ListResponse<Characters>> loadCharactersInComic(
            @Path("comicId") int id,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/comics/{comicId}/events")
    Call<ListResponse<Events>> loadEventsInComic(
            @Path("comicId") int id,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/events/{eventId}/characters")
    Call<ListResponse<Characters>> loadCharactersInEvent(
            @Path("eventId") int id,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/events/{eventId}/comics")
    Call<ListResponse<Comics>> loadComicsInEvent(
            @Path("eventId") int id,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/events/{eventId}/series")
    Call<ListResponse<Series>> loadSeriesInEvent(
            @Path("eventId") int id,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/series/{seriesId}/characters")
    Call<ListResponse<Characters>> loadCharactersInSeries(
            @Path("seriesId") int id,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/series/{seriesId}/comics")
    Call<ListResponse<Comics>> loadComicsInSeries(
            @Path("seriesId") int id,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/series/{seriesId}/events")
    Call<ListResponse<Events>> loadEventsInSeries(
            @Path("seriesId") int id,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/creators")
    Call<ListResponse<Creators>> loadCreators(
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/creators")
    Call<ListResponse<Creators>> loadSearchedCreators(
            @Query("nameStartsWith") String nameStart,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/creators/{creatorId}")
    Call<InfoResponse> loadCreator(
            @Path("creatorId") int id
    );

    @GET("/v1/public/creators/{creatorId}/comics")
    Call<ListResponse<Comics>> loadCreatorsComics(
            @Path("creatorId") int id,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/creators/{creatorId}/series")
    Call<ListResponse<Series>> loadCreatorsSeries(
            @Path("creatorId") int id,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("/v1/public/creators/{creatorId}/events")
    Call<ListResponse<Events>> loadCreatorsEvents(
            @Path("creatorId") int id,
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
