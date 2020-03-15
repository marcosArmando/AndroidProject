package com.yucatancorp.androidproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonGets {

    @GET("pokemon")
    Call<Resultados> listaPokemons(@Query("limit") int limit, @Query("offset") int offset);
}
