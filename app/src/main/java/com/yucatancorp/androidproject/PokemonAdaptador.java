package com.yucatancorp.androidproject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yucatancorp.androidproject.POJOs.Pokemon;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PokemonAdaptador extends RecyclerView.Adapter<PokemonAdaptador.PokemonViewHolder>{

    private ArrayList<Pokemon> pokemons;
    private Context context;

    public PokemonAdaptador(Context context){
        this.context = context;
        pokemons = new ArrayList<>();
    }

    public PokemonAdaptador(Context context, ArrayList<Pokemon> pokemons){
        this.context = context;
        this.pokemons = pokemons;
    }

    public ArrayList<Pokemon> listaPokemons(){
        return this.pokemons;
    }

    public void gettingData(ArrayList<Pokemon> pokemons) {
        this.pokemons.addAll(this.pokemons.size(), pokemons);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_card, parent, false);
        return new PokemonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder pokemonViewholder, int position) {

        Pokemon pokemonTemp = new Pokemon(pokemons.get(position).getName(), pokemons.get(position).getUrl());

        String[] sufijoUrlPokemon = pokemonTemp.getUrl().split("mon/");
        String[] numeroPokemonString = sufijoUrlPokemon[1].split("/");
        int numeroPokemon = Integer.valueOf(numeroPokemonString[0]);

        pokemonViewholder.textView.setText(pokemonTemp.getName());

        Glide.with(context).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+numeroPokemon+".png")
                .centerCrop()
                .transition(withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(pokemonViewholder.imageView);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public static class PokemonViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;

        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagenCV);
            textView = itemView.findViewById(R.id.nombrePokemonCV);
        }
    }
}
