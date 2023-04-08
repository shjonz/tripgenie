package com.example.fcctut;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class newLocations extends AppCompatActivity {

    // Declare variables
    private RecyclerView recyclerView;
    private PlaceAdapter placeAdapter;
    private PlacesClient placesClient;
    private AutocompleteSessionToken sessionToken;
    private AutoCompleteTextView edtSearch;
    private BottomNavigationView bottomNavigationView;
    private SearchSuggestionAdapter searchSuggestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_locations);

        // Initialize Places API client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), BuildConfig.WEB_API_KEY);
        }
        placesClient = Places.createClient(this);

        // Create AutocompleteSessionToken
        sessionToken = AutocompleteSessionToken.newInstance();

        // Initialize UI elements
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.addLocation);

        edtSearch = findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            // Call getAutocompletePredictions method after text changed
            @Override
            public void afterTextChanged(Editable s) {
                getAutocompletePredictions(s.toString());
            }
        });

        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // When an item in the dropdown list is clicked, set the text in the AutoCompleteTextView to the place name
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place place = (Place) parent.getItemAtPosition(position);
                edtSearch.setText(place.getName());
            }
            // TODO Retrieve the place in this search bar and listen for clicker of the add places button and send Place Name to Saved Places Page
        });

        // Initialize bottom navigation view and set item selected listener
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

        // Initialize RecyclerView and set adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.simple_divider));

        // Initialize button and set click listener to fetch places
        Button getSuggestionsButton = findViewById(R.id.getSuggestionsButton);
        getSuggestionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Get coordinates of city from Plan Trip Page and override this hardcoded coordinates

                // Set city coordinates and fetch places using Places API helper class
                double cityLatitude = 40.7128;
                double cityLongitude = -74.0060;

                PlacesApiHelper.fetchPlaces(cityLatitude, cityLongitude, 50000, "tourist_attraction", "prominence", BuildConfig.WEB_API_KEY, new PlacesApiHelper.PlacesApiCallback() {
                    @Override
                    public void onPlacesFetched(List<Place> places) {
                        // Update UI with fetched places
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

    // Method to get autocomplete predictions for a query
    private void getAutocompletePredictions(String query) {
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setSessionToken(sessionToken)
                .setQuery(query)
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .build();

        // Call Places API to get autocomplete predictions
        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            // Convert AutocompletePrediction objects to Place objects and update search suggestion adapter
            List<Place> places = new ArrayList<>();
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                String placeId = prediction.getPlaceId();
                CharSequence primaryText = prediction.getPrimaryText(null); // This is the name of the place
                CharSequence secondaryText = prediction.getSecondaryText(null); // This is the address of the place
                places.add(new Place(placeId, primaryText.toString(), secondaryText.toString()));
            }

            updateSearchSuggestionAdapter(places);
        }).addOnFailureListener((exception) -> {
            Log.e("newLocations", "Autocomplete prediction fetch failed", exception);
        });
    }

    // Method to update search suggestion adapter with new data
    private void updateSearchSuggestionAdapter(List<Place> places) {
        if (searchSuggestionAdapter == null) {
            // If adapter is null, create new adapter and set it to AutoCompleteTextView
            searchSuggestionAdapter = new SearchSuggestionAdapter(newLocations.this, places);
            edtSearch.setAdapter(searchSuggestionAdapter);
        } else {
            // If adapter exists, clear old data and add new data, then notify adapter
            searchSuggestionAdapter.clear();
            searchSuggestionAdapter.addAll(places);
            searchSuggestionAdapter.notifyDataSetChanged();
        }
    }
}


