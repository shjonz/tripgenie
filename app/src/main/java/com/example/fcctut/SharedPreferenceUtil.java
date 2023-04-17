/*
 * SharedPreferenceUtil.java
 * This class provides methods to save and retrieve a list of Place objects from Android's SharedPreferences.
 * It uses Google's Gson library for JSON serialization/deserialization.
 */

package com.example.fcctut;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferenceUtil {
    // Define the key used to store the list of saved places in the SharedPreferences object
    private static final String SAVED_PLACES_KEY = "saved_places";

    /*
     * savePlaces(Context context, List<Place> places)
     * Saves a list of Place objects to the SharedPreferences object.
     * @param context The context of the application.
     * @param places The list of Place objects to save.
     */
    public static void savePlaces(Context context, List<Place> places) {
//        // Get the default SharedPreferences object
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        // Create an editor to modify the SharedPreferences object
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        // Convert the list of Place objects to a JSON string using Gson
//        Gson gson = new Gson();
//        String json = gson.toJson(places);
//        // Store the JSON string in the SharedPreferences object with the key "saved_places"
//        editor.putString(SAVED_PLACES_KEY, json);
//        editor.apply();




        //new update 16 april 2023
        // Get the default SharedPreferences object
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Create an editor to modify the SharedPreferences object
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Create a JSONArray to store the Place objects
        JSONArray jsonArray = new JSONArray();
        // Loop through the list of Place objects
        for (Place place : places) {
            try {
                // Create a JSONObject to store the Place object
                JSONObject jsonPlace = new JSONObject();
                System.out.println("inside shared preferences util .java file " +
                        place.getPlaceId() + " name " + place.getName() + " address " +
                        place.getAddress() + " lat " + place.getLatitude() + " lon "
                + place.getLongitude() + " opening_hours " + place.getOpeningHours() );

                // Add the existing fields to the JSONObject
                jsonPlace.put("place_id", place.getPlaceId());
                jsonPlace.put("name", place.getName());
                jsonPlace.put("address", place.getAddress());
                jsonPlace.put("rating", place.getPopularity());
                jsonPlace.put("distance", place.getDistance());
                // Add the new fields to the JSONObject
                jsonPlace.put("latitude", place.getLatitude());
                jsonPlace.put("longitude", place.getLongitude());
                // Use Gson to convert the OpeningHours object to a JSON string and add it to the JSONObject
                Gson gson = new Gson();
                Place.OpeningHours openingHours = place.getOpeningHours();
                if (openingHours != null) {
                    String openingHoursJson = gson.toJson(openingHours);
                    jsonPlace.put("opening_hours", new JSONObject(openingHoursJson));
                } else {
                    jsonPlace.put("opening_hours", JSONObject.NULL);
                }
                //jsonPlace.put("opening_hours", new JSONObject(openingHoursJson));
                // Add the JSONObject to the JSONArray
                jsonArray.put(jsonPlace);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // Convert the JSONArray to a JSON string and save it in the SharedPreferences object
        String json = jsonArray.toString();
        editor.putString(SAVED_PLACES_KEY, json);
        editor.apply(); // use apply() instead of commit() for better performance



    }

    /*
     * getSavedPlaces(Context context)
     * Retrieves a list of saved Place objects from the SharedPreferences object.
     * @param context The context of the application.
     * @return A list of Place objects, or an empty ArrayList if no places have been saved.
     */
    public static List<Place> getSavedPlaces(Context context) {
        // Get the default SharedPreferences object
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        // Retrieve the JSON string stored in the SharedPreferences object with the key "saved_places"
        Gson gson = new Gson();
        String json = sharedPreferences.getString(SAVED_PLACES_KEY, null);
        // Convert the JSON string back to a list of Place objects using Gson and TypeToken
        Type type = new TypeToken<ArrayList<Place>>() {}.getType();
        List<Place> places = gson.fromJson(json, type);
        // Return an empty ArrayList if no places have been saved or an error occurred during the conversion process
        if (places == null) {
            places = new ArrayList<>();
        }
        return places;
    }
}
