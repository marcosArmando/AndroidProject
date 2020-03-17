package com.yucatancorp.androidproject.POJOs;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Resultado {

    @SerializedName("count")
    private int count;
    @SerializedName("next")
    private String next;
    @SerializedName("previous")
    private String previous;
    @SerializedName("results")
    private ArrayList<Pokemon> results;

    public Resultado(int count, String next, String previous, ArrayList<Pokemon> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public ArrayList<Pokemon> getResults() {
        return results;
    }

}
