// Import necessary classes
package com.example.fcctut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.List;

// Create the newLocations activity, which extends AppCompatActivity
public class newLocations extends AppCompatActivity {
    // Declare variables for the RecyclerView and PlaceAdapter
    private RecyclerView recyclerView;
    private PlaceAdapter placeAdapter;

    // onCreate method is called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the XML layout file (activity_new_locations)
        setContentView(R.layout.activity_new_locations);

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        // Set the layout manager to LinearLayoutManager, which lays out items in a linear fashion
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Add the divider item decoration to the RecyclerView
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.simple_divider));

        // Initialize the getSuggestionsButton and set its click listener
        Button getSuggestionsButton = findViewById(R.id.getSuggestionsButton);
        getSuggestionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Get the coordinates of the City they are planning the trip for from the "Plan Trip" page and call the getSuggestions method
                // Define the latitude and longitude of the city
                double cityLatitude = 40.7128;
                double cityLongitude = -74.0060;

                // Fetch places using the PlacesApiHelper
                PlacesApiHelper.fetchPlaces(cityLatitude, cityLongitude, 50000, "tourist_attraction", "prominence", BuildConfig.WEB_API_KEY, new PlacesApiHelper.PlacesApiCallback() {
                    // onPlacesFetched method is called when the places are successfully fetched
                    @Override
                    public void onPlacesFetched(List<Place> places) {
                        // Update the UI with the fetched places
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Create a new PlaceAdapter with the fetched places and set it as the adapter for the RecyclerView
                                placeAdapter = new PlaceAdapter(places);
                                recyclerView.setAdapter(placeAdapter);
                                // Log the number of fetched places for debugging purposes
                                Log.d("newLocations", "Places fetched: " + places.size());
                            }
                        });
                    }

                    // onFailure method is called if there's an error fetching the places
                    @Override
                    public void onFailure() {
                        // Handle the failure, such as displaying an error message
                        Log.e("newLocations", "Failed to fetch places");
                    }
                });
            }
        });
    }
}
