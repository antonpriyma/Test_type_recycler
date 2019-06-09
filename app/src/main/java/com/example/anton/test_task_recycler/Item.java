package com.example.anton.test_task_recycler;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.MalformedURLException;
import java.net.URL;

public class Item implements Parcelable {

    @SerializedName("message")
    @Expose
    private URL url;

    {
        try {
            url = new URL("https://images.dog.ceo/breeds/hound-basset/n02088238_490.jpg");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private String name="dog";

    Item(URL url){
        this.url=url;
    }

    Item(){

    }

    public URL getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.url);
        dest.writeString(this.name);
    }

    protected Item(Parcel in) {
        this.url = (URL) in.readSerializable();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
