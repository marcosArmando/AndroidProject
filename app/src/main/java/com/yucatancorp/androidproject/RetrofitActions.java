package com.yucatancorp.androidproject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActions {

    private Retrofit retrofit;
    private String baseURL = "https://pokeapi.co/api/v2/";

    public void cargarPokemons() {

        retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        PokemonGets pokemon = retrofit.create(PokemonGets.class);

        Call<Resultados> resultadosObtenidos = pokemon.listaPokemons(20, 20);

        resultadosObtenidos.enqueue(new Callback<Resultados>() {
            @Override
            public void onResponse(Call<Resultados> call, Response<Resultados> response) {

                if (response.isSuccessful()){

                    Resultados resultados = response.body();
                    ArrayList<Pokemon> pokemons = resultados.getPokemons();
                }
            }

            @Override
            public void onFailure(Call<Resultados> call, Throwable t) {

            }
        });
    }

}
