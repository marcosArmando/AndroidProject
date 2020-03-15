package com.yucatancorp.androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class PokemonAdaptador extends RecyclerView.Adapter<PokemonAdaptador.PokemonViewHolder>{
    private ArrayList<Pokemon> pokemons;

    public PokemonAdaptador(ArrayList<Pokemon> pokemons){
        this.pokemons = pokemons;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_card, parent, false);
        return new PokemonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder pokemonViewholder, int position) {

        BajarImagenes bajarImagenes = new BajarImagenes();

        try {
            pokemonViewholder.imageView.setImageBitmap(bajarImagenes.execute(Integer.toString(position)).get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //DownloadPokemon downloadPokemon = new DownloadPokemon();
        //pokemonViewholder.textView.setText(tempPokemon.getName());
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
