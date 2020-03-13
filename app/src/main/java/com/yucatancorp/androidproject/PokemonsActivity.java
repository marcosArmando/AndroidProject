package com.yucatancorp.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import static com.yucatancorp.androidproject.MainActivity.STATUS;

public class PokemonsActivity extends AppCompatActivity {

    SharedPreferences sharedPreferencesStatus;
    SharedPreferencesActions sharedPreferencesActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons);

        sharedPreferencesStatus = getApplicationContext().getSharedPreferences(STATUS, 0);

        SharedPreferencesActions sharedPreferencesActions = new SharedPreferencesActions(sharedPreferencesStatus);


    }
}
