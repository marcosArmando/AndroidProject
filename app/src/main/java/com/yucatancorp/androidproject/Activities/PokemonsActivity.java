package com.yucatancorp.androidproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.yucatancorp.androidproject.miscellaneousActions.IntentsAActivities;
import com.yucatancorp.androidproject.R;
import com.yucatancorp.androidproject.miscellaneousActions.SharedPreferencesActions;


import static com.yucatancorp.androidproject.Activities.MainActivity.STATUS;

public class PokemonsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemons);




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

            SharedPreferences sharedPreferences = PokemonsActivity.this.getSharedPreferences(STATUS, 0);
            SharedPreferencesActions sharedPreferencesActions = new SharedPreferencesActions(sharedPreferences);
            sharedPreferencesActions.changeStatus(false);

            IntentsAActivities.irLogInActivity(PokemonsActivity.this);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

}