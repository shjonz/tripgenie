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

    // to be called when Trips are first made
    public Trip(String cityName, String startDate, String endDate) {
        this.cityName = cityName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.savedPlaces = new ArrayList<>();
        this.days = new ArrayList<ArrayList<Place>>();
        for (int i=0; i<4; i++) { //TODO: fix hardcoded days
            this.days.add(new ArrayList<Place>());
        }
    }

    // to be called when Trips are first made
    public Trip(int k, String cityName, String startDate, String endDate) {
        this.cityName = cityName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.savedPlaces = new ArrayList<>();
        this.days = new ArrayList<ArrayList<Place>>();
        for (int i=0; i<k; i++) {
            this.days.add(new ArrayList<Place>());
        }
    }

    // constructor to construct saved JSON trip back into a Trip object
//    public Trip(String savedTrip) {
//        Log.d("testing Trip", "Trip constructor called: " + savedTrip.toString());
//
//        Iterator<?> keys = savedTrip.keys();
//        while(keys.hasNext() ) {
//            String key = (String)keys.next();
//            if (key.equals("startDate")) {
//                Log.d("testing Trip", "called startDate ");
//                try {
//                    this.startDate = savedTrip.get(key).toString();
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            if (key.equals("endDate")) {
//                try {
//                    this.endDate = savedTrip.get(key).toString();
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            try {
//                if ( savedTrip.get(key) instanceof JSONObject ) {
//                    JSONObject place = new JSONObject(savedTrip.get(key).toString());
//                    places.put(key, JSONObjectToPlace(place));
//                }
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    // takes in JSONObject of a saved place and turns it in to a place object
    // TODO: update fields to include attributes shoj needs
//    public Place JSONObjectToPlace(JSONObject jsonObject) {
//        String name;
//        String address;
//        Double popularity;
//        String placeId;
//        Double latitude;
//        Double longitude;
//        Place place;
//        try {
//            name = jsonObject.get("name").toString();
//            address = jsonObject.get("address").toString();
//            popularity = Double.valueOf(jsonObject.get("popularity").toString());
//            placeId = jsonObject.get("placeId").toString();
//            if (jsonObject.has("latitude") && jsonObject.has("longitude")) {
//                latitude = Double.valueOf(jsonObject.get("latitude").toString());
//                longitude = Double.valueOf(jsonObject.get("longitude").toString());
//                place = new Place(placeId, name, address, popularity, latitude, longitude);
//            } else {
//                place = new Place(placeId, name, address, popularity);
//            }
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//        return place;
//    }
//
//    public JSONObject placeToJSONObject(Place place) {
//        JSONObject jsonObject = new JSONObject();
//
//        try {
//            jsonObject.put("placeId", place.getPlaceId());
//            jsonObject.put("name", place.getName());
//            jsonObject.put("popularity", place.getPopularity());
//            jsonObject.put("address", place.getAddress());
//            if (place.getGeometry() != null) {
//                jsonObject.put("latitude", place.getGeometry().getLocation().getLatitude()); // part of geometry
//                jsonObject.put("longitude", place.getGeometry().getLocation().getLongitude()); // part of geometry
//            }
//
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//        return jsonObject;
//    }

    // saves Trip into filename
//    public void saveToTripFile(Context ctx, String filename) {
//        JSONObject json = new JSONObject();
//        try {
//            json.put("startDate", this.startDate);
//            json.put("endDate", this.endDate);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//        for (String tripName: places.keySet()) {
//            Place p = places.get(tripName);
//            try {
//                json.put(tripName, p.getAllAttributes());
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//        }
////        Log.d("testing Trip", json.toString());
//        FileManager.writeToFile(ctx, filename, json);
////        FileManager.writeFileOnInternalStorage(ctx, filename, json.toString());
//    }

//    @NonNull
//    @Override
//    public String toString() {
//        String ret = "";
//        for (Place p: places.values()) {
//            ret += p.toString() + "\n";
//        }
//        return ret;
//    }
}
