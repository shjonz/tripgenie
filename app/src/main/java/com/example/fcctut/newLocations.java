package com.example.fcctut;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class newLocations extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PlaceAdapter placeAdapter;
    private PlacesClient placesClient;
    private AutocompleteSessionToken sessionToken;
    private EditText edtSearch;
    private ProgressBar progressBar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_locations);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.addLocation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                    case R.id.itinerary:
                        startActivity(new Intent(getApplicationContext(), showItinerary.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.savedLocations:
                        startActivity(new Intent(getApplicationContext(), SavedLocations.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.addLocation:
                        startActivity(new Intent(getApplicationContext(), newLocations.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.simple_divider));
        Button getSuggestionsButton = findViewById(R.id.getSuggestionsButton);
        getSuggestionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double cityLatitude = 40.7128;
                double cityLongitude = -74.0060;

                PlacesApiHelper.fetchPlaces(cityLatitude, cityLongitude, 50000, "tourist_attraction", "prominence", BuildConfig.WEB_API_KEY, new PlacesApiHelper.PlacesApiCallback() {
                    @Override
                    public void onPlacesFetched(List<Place> places) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                placeAdapter = new PlaceAdapter(places);
                                recyclerView.setAdapter(placeAdapter);
                                Log.d("newLocations", "Places fetched: " + places.size());
                            }
                        });
                    }

                    @Override
                    public void onFailure() {
                        Log.e("newLocations", "Failed to fetch places");
                    }
                });
            }
        });
    }
}