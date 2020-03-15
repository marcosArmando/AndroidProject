package com.yucatancorp.androidproject;

import com.google.gson.annotations.SerializedName;

public class Pokemon {

    private int fotoPokemon;
    private String nombrePokemon;

    public Pokemon(int fotoPokemon, String nombrePokemon){
        this.fotoPokemon = fotoPokemon;
        this.nombrePokemon = nombrePokemon;
    }

    public int getFotoPokemon() {
        return fotoPokemon;

    }

    public void setFotoPokemon(int fotoPokemon) {
        this.fotoPokemon = fotoPokemon;
    }

    public String getNombrePokemon() {
        return nombrePokemon;
    }

    public void setNombrePokemon(String nombrePokemon) {
        this.nombrePokemon = nombrePokemon;
    }
}
