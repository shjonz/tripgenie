package com.example.fcctut;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Trip {
    @SerializedName("cityName")
    String cityName;
    @SerializedName("startDate")
    String startDate;
    @SerializedName("endDate")
    String endDate;
    @SerializedName("latitude")
    Double latitude;
    @SerializedName("longitude")
    Double longitude;

    @SerializedName("days")
    public ArrayList<ArrayList<Place>> days;
    @SerializedName("savedPlaces")
    ArrayList<Place> savedPlaces;
    @SerializedName("numberOfDays")
    int numberOfDays;

    // to be called when Trips are first made
    public Trip(int k, String cityName, String startDate, String endDate) {
        this.cityName = cityName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.savedPlaces = new ArrayList<>();
        this.numberOfDays = k;
        this.days = new ArrayList<ArrayList<Place>>();
        for (int i=0; i<k; i++) {
            this.days.add(new ArrayList<Place>());
        }
    }
}
