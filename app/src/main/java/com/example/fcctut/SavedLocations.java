package com.example.fcctut;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.List;

// SavedLocations is an Activity that displays a list of saved locations using a RecyclerView
public class SavedLocations extends AppCompatActivity implements SavedPlacesAdapter.OnPlaceClickListener {

    // Declare member variables
    private RecyclerView savedLocationsRecyclerView; // RecyclerView to display the saved locations
    private SavedPlacesAdapter savedPlacesAdapter; // Custom adapter to display saved locations
    private List<Place> savedPlaces; // List of Place objects representing saved locations
    private Button homebutton;

    // Called when the activity is starting
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for this activity
        setContentView(R.layout.saved_locations);

        homebutton = findViewById(R.id.homepagebutton);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SavedLocations.this, ProfileActivity2.class);
                startActivity(intent);
            }
        });

        // Fetch saved places from SharedPreferences
        savedPlaces = SharedPreferenceUtil.getSavedPlaces(this);

        // Initialize the RecyclerView
        savedLocationsRecyclerView = findViewById(R.id.savedLocationsRecyclerView);
        // Set the LayoutManager for the RecyclerView
        savedLocationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the SavedPlacesAdapter with the savedPlaces list and OnPlaceClickListener
        savedPlacesAdapter = new SavedPlacesAdapter(this, savedPlaces, this);
        // Set the adapter for the RecyclerView
        savedLocationsRecyclerView.setAdapter(savedPlacesAdapter);

        // Initialize the BottomNavigationView
        BottomNavigationView bottomNavigationView;

        // Code to navigate the bottom navigation bar
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // Set the selected item to "savedLocations"
        bottomNavigationView.setSelectedItemId(R.id.savedLocations);
        // Set the OnItemSelectedListener for the bottom navigation bar
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item selection
                switch (item.getItemId()) {
////                    case R.id.home:
////                        // Navigate to MainActivity
////                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        // Apply transition animation
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.maps:
                        // Navigate to MapsActivity
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        // Apply transition animation
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.itinerary:
                        // Navigate to showItinerary activity
                        startActivity(new Intent(getApplicationContext(), showItinerary.class));
                        // Apply transition animation
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.addLocation:
                        startActivity(new Intent(getApplicationContext(), newLocations.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.savedLocations:
                        // Stay in the current activity (SavedLocations)
                        return true;
                }
                return false;
            }
        }); // End of Bottom Navigation Bar code
    } // End of onCreate method

    // Handle place click events
    @Override
    public void onPlaceClick(int position) {
        Place selectedPlace = savedPlaces.get(position);
        // Perform the desired action with the selected place, e.g., add to itinerary
    }

}