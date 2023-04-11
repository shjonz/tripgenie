/*
* RecommendationsActivity.java
* This activity displays recommendations for places of interest and places to eat based on the user's current location.
* It uses the Places API to fetch information about nearby places and displays them in a RecyclerView using the RecommendationAdapter.
*/

package com.example.fcctut;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecommendationsActivity extends AppCompatActivity {

   private RecyclerView recyclerView;
   private RecommendationAdapter adapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_recommendations);

       // Get a reference to the RecyclerView and set its layout manager
       recyclerView = findViewById(R.id.recommendations_recycler_view);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

       // Get the user's current latitude and longitude from the intent extras
       double currentLatitude = getIntent().getDoubleExtra("latitude", 0);
       double currentLongitude = getIntent().getDoubleExtra("longitude", 0);

       Log.d("RecommendationsActivity", "Latitude: " + currentLatitude);
       Log.d("RecommendationsActivity", "Longitude: " + currentLongitude);

       // Get the API key from the BuildConfig file
       String apiKey = BuildConfig.WEB_API_KEY;
       Log.d("RecommendationsActivity", apiKey);

       // Fetch recommendations based on the user's location using the Places API
       fetchRecommendations(currentLatitude, currentLongitude, apiKey);
   }

   /*
    * fetchPlacesAndDisplay(int index, double latitude, double longitude, String placeType, int count, String apiKey, String rankBy, Runnable onCompleted)
    * Fetches a list of nearby places of the given placeType and displays them in the adapter starting at the given index.
    * @param index The index at which to start displaying the fetched places in the adapter.
    * @param latitude The latitude of the location to search for nearby places.
    * @param longitude The longitude of the location to search for nearby places.
    * @param placeType The type of place to search for (e.g. "tourist_attraction" or "restaurant").
    * @param count The maximum number of places to fetch and display.
    * @param apiKey The API key to use for the Places API.
    * @param rankBy The ranking method to use for the Places API (e.g. "prominence" or "distance").
    * @param onCompleted A Runnable to be executed after the places have been fetched and displayed (can be null).
    */
   private void fetchPlacesAndDisplay(int index, double latitude, double longitude, String placeType, int count, String apiKey, String rankBy, Runnable onCompleted) {
       int radius = 10000;

       PlacesApiHelper.fetchPlaces(latitude, longitude, radius, placeType, rankBy, apiKey, new PlacesApiHelper.PlacesApiCallback() {
           @Override
           public void onPlacesFetched(List<Place> places) {
               Log.d("PlacesApiHelper", "onPlacesFetched: " + places.size() + " places found");

               // Sort the list of fetched places and add them to the adapter
               List<Place> sortedPlaces = new ArrayList<>(places.subList(0, Math.min(count, places.size())));
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       adapter.addPlaces(index, sortedPlaces);
                       if (onCompleted != null) {
                           onCompleted.run();
                       }
                   }
               });
           }

           @Override
           public void onFailure() {
               Log.d("PlacesApiHelper", "onFailure: Failed to fetch recommendations");

               // Show a toast message to the user if the places could not be fetched
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(RecommendationsActivity.this, "Failed to fetch recommendations", Toast.LENGTH_SHORT).show();
                   }
               });
           }
       });
   }

   /*
    * fetchRecommendations(double latitude, double longitude, String apiKey)
    * Fetches recommendations for places of interest and places to eat based on the user's current location.
    * @param latitude The latitude of the user's current location.
    * @param longitude The longitude of the user's current location.
    * @param apiKey The API key to use for the Places API.
    */
   private void fetchRecommendations(double latitude, double longitude, String apiKey) {
       List<Object> recommendations = new ArrayList<>();

       // Create a new adapter and set it to the RecyclerView
       adapter = new RecommendationAdapter(this, recommendations);
       recyclerView.setAdapter(adapter);

       // Fetch and display places of interest
       recommendations.add("Places of Interest");
       fetchPlacesAndDisplay(recommendations.size(), latitude, longitude, "tourist_attraction", 10, apiKey, "prominence", new Runnable() {
           @Override
           public void run() {
               // Fetch and display places to eat
               recommendations.add("Places to Eat");
               fetchPlacesAndDisplay(recommendations.size(), latitude, longitude, "restaurant|cafe|bakery|bar", 5, apiKey, "prominence", null);
           }
       });
   }
}



