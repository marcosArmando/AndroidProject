package com.yucatancorp.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.yucatancorp.androidproject.POJOs.Pokemon;
import com.yucatancorp.androidproject.POJOs.Resultado;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yucatancorp.androidproject.MainActivity.STATUS;

public class PokemonsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferencesStatus;
    SharedPreferencesActions sharedPreferencesActions;

    RecyclerView recyclerView;

    ArrayList<Pokemon> pokemons = new ArrayList<>();

    Retrofit retrofit;

    private String baseURL = "https://pokeapi.co/api/v2/";

    PokemonAdaptador pokemonAdaptador;

    private int offset;
    private boolean puedeCargar;

    private Parcelable savedRecyclerLayoutState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons);

        recyclerView = findViewById(R.id.recyclerView);

        int numberofCV = 3;
        offset = 0;

        if (savedInstanceState != null) {
            pokemons = savedInstanceState.getParcelableArrayList("pokemonsRe");
            savedRecyclerLayoutState = savedInstanceState.getParcelable("estanciaRV");
            pokemonAdaptador = new PokemonAdaptador(PokemonsActivity.this, pokemons);

        } else {
            pokemonAdaptador = new PokemonAdaptador(PokemonsActivity.this);
        }


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            numberofCV = 5;
        }
        GridLayoutManager glm = new GridLayoutManager(PokemonsActivity.this, numberofCV);
        recyclerView.setLayoutManager(glm);

        recyclerView.setAdapter(pokemonAdaptador);

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

        sharedPreferencesStatus = getApplicationContext().getSharedPreferences(STATUS, 0);
        SharedPreferencesActions sharedPreferencesActions = new SharedPreferencesActions(sharedPreferencesStatus);


        puedeCargar = true;
        cargarPokemons(offset);
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
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("pokemonsRe", pokemons);
        outState.putParcelable("estanciaRV", recyclerView.getLayoutManager().onSaveInstanceState());
    }
}