package com.example.fcctut;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// The RecommendationsActivity is responsible for displaying recommendations based on the user's location.
public class RecommendationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecommendationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        // Initialize the RecyclerView for displaying recommendations.
        recyclerView = findViewById(R.id.recommendations_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the current latitude and longitude from the Intent.
        double currentLatitude = getIntent().getDoubleExtra("latitude", 0);
        double currentLongitude = getIntent().getDoubleExtra("longitude", 0);

        // Log the received coordinates.
        Log.d("RecommendationsActivity", "Latitude: " + currentLatitude);
        Log.d("RecommendationsActivity", "Longitude: " + currentLongitude);

        // Get the API key from the resources.
        String apiKey = BuildConfig.WEB_API_KEY;
        Log.d("RecommendationsActivity", apiKey);

        // Fetch and display recommendations based on the user's location.
        fetchRecommendations(currentLatitude, currentLongitude, apiKey);

    }

    // Fetch nearby places and update the RecyclerView with the fetched places
    private void fetchPlacesAndDisplay(int index, double latitude, double longitude, String placeType, int count, String apiKey, Runnable onCompleted) {
        int radius = 10000; // Define the search radius in meters.

        // Call the Places API helper method to fetch nearby places.
        PlacesApiHelper.fetchPlaces(latitude, longitude, radius, placeType, apiKey, "prominence", new PlacesApiHelper.PlacesApiCallback() {
            @Override
            public void onPlacesFetched(List<Place> places) {
                Log.d("PlacesApiHelper", "onPlacesFetched: " + places.size() + " places found");

                // Sort the fetched places by distance and limit the list to the specified count
                List<Place> sortedPlaces = new ArrayList<>(places.subList(0, Math.min(count, places.size())));

                // Update the RecyclerView with the fetched places on the UI thread.
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

            // Handle failures when fetching recommendations.
            @Override
            public void onFailure() {
                Log.d("PlacesApiHelper", "onFailure: Failed to fetch recommendations");

                // Display a failure message on the UI thread.
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RecommendationsActivity.this, "Failed to fetch recommendations", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Fetch recommendations based on the user's location and update the RecyclerView
    private void fetchRecommendations(double latitude, double longitude, String apiKey) {
        List<Object> recommendations = new ArrayList<>();
        adapter = new RecommendationAdapter(recommendations);
        recyclerView.setAdapter(adapter);

        // Add headers for the two categories of recommendations
        recommendations.add("Places of Interest");
        // Fetch and display 10 nearest tourist attractions
        fetchPlacesAndDisplay(recommendations.size(), latitude, longitude, "tourist_attraction", 10, apiKey, new Runnable() {
            @Override
            public void run() {
                recommendations.add("Places to Eat");
                // Fetch and display 5 nearest food-related places
                fetchPlacesAndDisplay(recommendations.size(), latitude, longitude, "restaurant|cafe|bakery|bar", 5, apiKey, null);
            }
        });
    }
}

