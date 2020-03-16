package com.yucatancorp.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.res.Configuration;
import android.os.Bundle;

import com.yucatancorp.androidproject.POJOs.Pokemon;
import com.yucatancorp.androidproject.POJOs.Resultado;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<Pokemon> pokemons = new ArrayList<>();

    Retrofit retrofit;

    private String baseURL = "https://pokeapi.co/api/v2/";

    PokemonAdaptador pokemonAdaptador;

    private int offset;
    private boolean puedeCargar;
    public static final String TAG = "estadoRV";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons);

        int numberofCV = 3;
        offset = 0;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            numberofCV = 5;
        }

        GridLayoutManager glm = new GridLayoutManager(PokemonsActivity.this, numberofCV);

        if (savedInstanceState != null) {
            recyclerView = findViewById(R.id.recyclerView);
            pokemons = savedInstanceState.getParcelableArrayList("pokemonsRe");
            if (pokemons == null) {

            }
            pokemonAdaptador = new PokemonAdaptador(PokemonsActivity.this, pokemons);
            recyclerView.setLayoutManager(glm);
            recyclerView.setAdapter(pokemonAdaptador);
        } else {
            recyclerView = findViewById(R.id.recyclerView);
            pokemonAdaptador = new PokemonAdaptador(PokemonsActivity.this);
            recyclerView.setLayoutManager(glm);
            recyclerView.setAdapter(pokemonAdaptador);
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){

                    if (puedeCargar) {

                        if ((glm.getChildCount() + glm.getItemCount()) >= glm.findFirstCompletelyVisibleItemPosition()) {
                            puedeCargar = false;
                            offset += 50;
                            cargarPokemons(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();

        puedeCargar = true;
        cargarPokemons(offset);
    }

    @Override
    protected void onResume() {
        super.onResume();

        recyclerView = findViewById(R.id.recyclerView);
    }

    public void cargarPokemons(int offset) {

        retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        PokemonGets pokemon = retrofit.create(PokemonGets.class);

        Call<Resultado> resultadosObtenidos = pokemon.listaPokemons(50, offset);
        resultadosObtenidos.enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                puedeCargar = true;
                if (response.isSuccessful()) {
                    Resultado resultado = response.body();
                    ArrayList<Pokemon> pokemonsD = resultado.getResults();
                    pokemonAdaptador.gettingData(pokemonsD);
                }
            }
            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {
                puedeCargar = true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        pokemons = pokemonAdaptador.listaPokemons();
        outState.putParcelableArrayList("pokemonsRe", pokemons);
        outState.putParcelable(TAG, recyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }
}