package com.yucatancorp.androidproject;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yucatancorp.androidproject.Activities.PokemonsActivity;
import com.yucatancorp.androidproject.POJOs.Pokemon;
import com.yucatancorp.androidproject.POJOs.Resultado;
import com.yucatancorp.androidproject.miscellaneousActions.PokemonGets;
import com.yucatancorp.androidproject.miscellaneousActions.SharedPreferencesActions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yucatancorp.androidproject.Activities.MainActivity.STATUS;


/**
 * A simple {@link Fragment} subclass.
 */
public class PokemonsFragment extends Fragment {

    RecyclerView recyclerView;

    ArrayList<Pokemon> pokemons;

    Retrofit retrofit;

    private String baseURL = "https://pokeapi.co/api/v2/";

    PokemonAdaptador pokemonAdaptador;

    private int offset;
    private boolean puedeCargar;
    public static final String TAG = "estadoRV";

    private ProgressBar myProgressBar;

    private SharedPreferencesActions sharedPreferencesActions;
    private SharedPreferences sharedPreferences;

    private TextView myTextView;


    public PokemonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pokemons, container, false);

        pokemons = new ArrayList<>();

        myProgressBar = v.findViewById(R.id.myProgressBar);

        sharedPreferences = getContext().getSharedPreferences(STATUS, 0);
        sharedPreferencesActions = new SharedPreferencesActions(sharedPreferences);

        int numberofCV = 3;
        offset = 0;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            numberofCV = 5;
        }

        GridLayoutManager glm = new GridLayoutManager(getContext(), numberofCV);

        if (savedInstanceState != null) {
            recyclerView = v.findViewById(R.id.recyclerview);
            pokemons = savedInstanceState.getParcelableArrayList("pokemonsRe");
            pokemonAdaptador = new PokemonAdaptador(getContext(), pokemons);
            recyclerView.setLayoutManager(glm);
            recyclerView.setAdapter(pokemonAdaptador);
        } else {
            recyclerView = v.findViewById(R.id.recyclerview);
            pokemonAdaptador = new PokemonAdaptador(getContext());
            recyclerView.setLayoutManager(glm);
            recyclerView.setAdapter(pokemonAdaptador);
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (checkInternet()) {

                    if(dy > 0){

                        if (puedeCargar) {

                            if ((glm.getChildCount() + glm.getItemCount()) >= glm.findFirstCompletelyVisibleItemPosition()) {
                                puedeCargar = false;
                                offset += 50;
                                cargarPokemons(offset);
                            }

                        }
                    }
                } else {


                    recyclerView.setVisibility(View.GONE);
                    myProgressBar.setVisibility(View.GONE);
                    myTextView.setVisibility(View.VISIBLE);

                }

            }
        });

        retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();

        cargarPokemons(offset);

        return v;
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
                    recyclerView.setVisibility(View.VISIBLE);
                    myProgressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {
                puedeCargar = true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("pokemonsRe", pokemons);
        outState.putParcelable(TAG, recyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    public boolean checkInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();

    }
}
