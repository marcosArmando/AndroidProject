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
    private ArrayList<Pokemon> pokemonsR;

    public void cargarPokemons(ArrayList<Pokemon> pokemons) {

        pokemonsR = pokemons;

        retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        PokemonGets pokemon = retrofit.create(PokemonGets.class);

        Call<Resultado> resultadosObtenidos = pokemon.listaPokemons(20, 20);

        resultadosObtenidos.enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {

                Resultado resultado = response.body();
                pokemonsR = resultado.getResults();

            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });
    }

    public ArrayList<Pokemon> getPokemonsR() {
        return pokemonsR;
    }
}
