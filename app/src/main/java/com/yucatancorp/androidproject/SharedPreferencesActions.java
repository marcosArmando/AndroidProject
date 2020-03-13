package com.yucatancorp.androidproject;

import android.content.SharedPreferences;

import static com.yucatancorp.androidproject.MainActivity.STATUS;
import static com.yucatancorp.androidproject.MainActivity.USUARIOLOGUEADO;

public class SharedPreferencesActions {

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesStatus;

    public SharedPreferencesActions(SharedPreferences sharedPreferences, SharedPreferences sharedPreferencesStatus){
        this.sharedPreferences = sharedPreferences;
        this.sharedPreferencesStatus = sharedPreferencesStatus;
    }

    public SharedPreferencesActions(SharedPreferences sharedPreferencesStatus){
        this.sharedPreferencesStatus = sharedPreferencesStatus;
    }

    public boolean checarLogIn() {

        return sharedPreferencesStatus.getBoolean(USUARIOLOGUEADO, false);

    }

    public void changeStatusToTrue() {

        SharedPreferences.Editor editor = sharedPreferencesStatus.edit();
        editor.putBoolean(STATUS, true);
        editor.apply();

    }

    public void changeStatusToFalse() {

        SharedPreferences.Editor editor = sharedPreferencesStatus.edit();
        editor.putBoolean(STATUS, false);
        editor.apply();

    }

    public void registrarUsuarioNuevo(String nombre, String password){

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(nombre, password);
        editor.apply();
    }
}
