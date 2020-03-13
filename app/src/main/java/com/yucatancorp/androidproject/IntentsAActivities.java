package com.yucatancorp.androidproject;

import android.content.Context;
import android.content.Intent;

public class IntentsAActivities {

    public static void irPokemonActivity(Context context){
        context.startActivity(new Intent(context, PokemonsActivity.class));
    }

    public static void irLogInActivity(Context context){
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
