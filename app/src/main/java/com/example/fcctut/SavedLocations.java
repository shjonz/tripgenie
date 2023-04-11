package com.example.fcctut;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.util.Log;
import java.util.Map;
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

        // save savedPlaces to local storage
        SharedPreferences sharedPref = getSharedPreferences("fileNameSaver", Context.MODE_PRIVATE);
        String filename = sharedPref.getString("currentFile", "");
        Trip trip = FileManager.getTrip(SavedLocations.this, filename);
        trip.savedPlaces = new ArrayList<>(savedPlaces);
        FileManager.saveTrip(SavedLocations.this, filename, trip);

        testOpeningHours();

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
    private void testOpeningHours() {
        String sampleJson = "{" +
                "\"weekday_text\": [" +
                "\"Monday: 9:00 AM – 5:00 PM\"," +
                "\"Tuesday: 9:00 AM – 5:00 PM\"," +
                "\"Wednesday: 9:00 AM – 5:00 PM\"," +
                "\"Thursday: 9:00 AM – 5:00 PM\"," +
                "\"Friday: 9:00 AM – 5:00 PM\"," +
                "\"Saturday: 10:00 AM – 4:00 PM\"," +
                "\"Sunday: Closed\"" +
                "]" +
                "}";

        Gson gson = new Gson();
        Place.OpeningHours openingHours = gson.fromJson(sampleJson, Place.OpeningHours.class);

        Map<String, String> openingHoursMap = openingHours.getOpeningHours();
        Map<String, String> closingHoursMap = openingHours.getClosingHours();


}

//        for (String day : openingHoursMap.keySet()) {
//            Log.d("OpeningHours", day + ": Opening - " + openingHoursMap.get(day) + ", Closing - " + closingHoursMap.get(day));
//            Log.d("Opening Time",openingHoursMap.get(day));
//            Log.d("Closing Time",closingHoursMap.get(day));
//        }
    }




