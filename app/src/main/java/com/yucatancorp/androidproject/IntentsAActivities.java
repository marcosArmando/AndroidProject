package com.yucatancorp.androidproject;

import android.content.Context;
import android.content.Intent;

import com.yucatancorp.androidproject.Activities.MainActivity;
import com.yucatancorp.androidproject.Activities.PokemonsActivity;

public class IntentsAActivities {

    public static void irPokemonActivity(Context context){
        context.startActivity(new Intent(context, PokemonsActivity.class));
    }

    public static void irLogInActivity(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
