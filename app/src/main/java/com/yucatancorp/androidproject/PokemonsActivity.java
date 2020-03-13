package com.yucatancorp.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.yucatancorp.androidproject.MainActivity.STATUS;

public class PokemonsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferencesStatus;
    SharedPreferencesActions sharedPreferencesActions;

    RecyclerView recyclerView;

    ArrayList<Pokemon> pokemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons);

        recyclerView = findViewById(R.id.recyclerView);
        pokemons = new ArrayList<>();

        pokemons.add(new Pokemon(0, "n"));
        pokemons.add(new Pokemon(0, "n"));
        pokemons.add(new Pokemon(0, "n"));
        pokemons.add(new Pokemon(0, "n"));
        pokemons.add(new Pokemon(0, "n"));

        GridLayoutManager glm = new GridLayoutManager(PokemonsActivity.this, 3);
        recyclerView.setLayoutManager(glm);
        PokemonAdaptador pokemonAdaptador = new PokemonAdaptador(pokemons);

        recyclerView.setAdapter(pokemonAdaptador);

        sharedPreferencesStatus = getApplicationContext().getSharedPreferences(STATUS, 0);
        SharedPreferencesActions sharedPreferencesActions = new SharedPreferencesActions(sharedPreferencesStatus);

    }

}
