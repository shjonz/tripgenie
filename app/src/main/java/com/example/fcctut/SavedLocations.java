package com.example.fcctut;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class SavedLocations extends AppCompatActivity {

    ArrayList<PlacesDetailsModel> placesDetailsModels= new ArrayList<>();
    ArrayList<String> randomarray= new ArrayList<>();
    ListView itineraryList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_locations);

        BottomNavigationView bottomNavigationView;

        //Saved Locations Recycler View code
        RecyclerView savedLocationsRecyclerView = findViewById(R.id.savedLocationsRecyclerView);

//        setUpPlacesDetailsModels();

        PlacesDetailsRecyclerViewAdapter adapter = new PlacesDetailsRecyclerViewAdapter(this, placesDetailsModels);
        savedLocationsRecyclerView.setAdapter(adapter);
        savedLocationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //code to navigate bottom navbar
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.savedLocations);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.maps:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
//                    case R.id.addLocation:
//                        startActivity(new Intent(getApplicationContext(), newLocations.class));
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.itinerary:
                        startActivity(new Intent(getApplicationContext(), showItinerary.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.savedLocations:

                        return true;
                }
                return false;
            }
        });// end of Bottom NavBar code

        itineraryList = findViewById(R.id.itineraryList);

        ArrayList<String> itineraries = new ArrayList<>();
        itineraries.add("Place 1");
        itineraries.add("Place 2");
        itineraries.add("Place 3");
        itineraries.add("Place 4");
        itineraries.add("Place 6");
        itineraries.add("Place 7");
        itineraries.add("Place 8");
        itineraries.add("Place 9");
        itineraries.add("Place 10");
        itineraries.add("Place 11");
        itineraries.add("Place 12");
        itineraries.add("Place 13");



        ArrayAdapter<String> itineraryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                itineraries
        );

        itineraryList.setAdapter(itineraryAdapter);

    }// end of OnCreate


    public void setUpPlacesDetailsModels(){
        String[] placesDetailsAddresses = getResources().getStringArray(R.array.places_details_addresses);
        String[] placesDetailsOpeningHours = getResources().getStringArray(R.array.places_details_opening_hours);

        randomarray.add("Place 1");


        //create model based on what iteration we are in for each item
//        for (int i=0;i< placesDetailsAddresses.length-1;){
//            placesDetailsModels.add(new PlacesDetailsModel(
//                    placesDetailsAddresses[i],
//                    placesDetailsOpeningHours[i]));
//        }

    }
}
