package com.yucatancorp.androidproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yucatancorp.androidproject.miscellaneousActions.IntentsAActivities;
import com.yucatancorp.androidproject.POJOs.Pokemon;
import com.yucatancorp.androidproject.POJOs.Resultado;
import com.yucatancorp.androidproject.PokemonAdaptador;
import com.yucatancorp.androidproject.miscellaneousActions.PokemonGets;
import com.yucatancorp.androidproject.R;
import com.yucatancorp.androidproject.miscellaneousActions.SharedPreferencesActions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yucatancorp.androidproject.Activities.MainActivity.STATUS;

public class PokemonsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ArrayList<Pokemon> pokemons = new ArrayList<>();

    Retrofit retrofit;

    private String baseURL = "https://pokeapi.co/api/v2/";

    PokemonAdaptador pokemonAdaptador;

    private int offset;
    private boolean puedeCargar;
    public static final String TAG = "estadoRV";

    private SharedPreferencesActions sharedPreferencesActions;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons);

        sharedPreferences = getApplicationContext().getSharedPreferences(STATUS, 0);
        sharedPreferencesActions = new SharedPreferencesActions(sharedPreferences);

        int numberofCV = 3;
        offset = 0;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            numberofCV = 5;
        }

        GridLayoutManager glm = new GridLayoutManager(PokemonsActivity.this, numberofCV);

        if (savedInstanceState != null) {
            recyclerView = findViewById(R.id.recyclerView);
            pokemons = savedInstanceState.getParcelableArrayList("pokemonsRe");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logOut) {
            sharedPreferencesActions.changeStatus(false);
            IntentsAActivities.irLogInActivity(PokemonsActivity.this);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }
}