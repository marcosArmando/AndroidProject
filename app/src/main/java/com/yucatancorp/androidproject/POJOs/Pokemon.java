package com.yucatancorp.androidproject.POJOs;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Pokemon implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;

    public Pokemon (String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {

        return name;
    }

    public Pokemon(Parcel in){
        this.name = in.readString();
        this.url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
