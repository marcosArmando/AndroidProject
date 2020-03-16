package com.yucatancorp.androidproject.miscellaneousActions;

import com.yucatancorp.androidproject.POJOs.Resultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonGets {

    @GET("pokemon")
    Call<Resultado> listaPokemons(@Query("limit") int limit, @Query("offset") int offset);
}
