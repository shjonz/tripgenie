package com.example.fcctut;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_REQUEST_CODE = 10001; //unique code
    private GoogleMap mMap; //for map fragment physical map
    private SearchView searchView; //searchView is the text bar that allows u to search for locations on the maps page

    //variables below are for obtaining current location

    //declare FusedLocationProviderClient to use Google Play Services Location API
    private static FusedLocationProviderClient fusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;
    private LocationCallback locationCallback;

    private Location mLastKnownLocation;
    private double searchedLatitude;
    private double searchedLongitude;
    private boolean isSearchLocationSet = false;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        searchView = findViewById(R.id.search_view_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //now below this is gonna be a function that listens for the text user inputs into the search bar to enter location
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder((MapsActivity.this));
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Add null check for addressList
                    if (addressList != null && !addressList.isEmpty()) {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        // Save the searched location's latitude and longitude
                        searchedLatitude = address.getLatitude();
                        searchedLongitude = address.getLongitude();
                        isSearchLocationSet = true;
                    } else {
                        // Handle the case when addressList is null or empty, e.g., show a message to the user
                        Toast.makeText(MapsActivity.this, "Unable to find the location. Please try a different search.", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            } //end of on query text submit function

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        }); //end of function for listening to search bar input

        mapFragment.getMapAsync(this); //this will update the map.
        //initialise FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Find the button for getting recommendations from the layout
        Button getRecommendationsButton = findViewById(R.id.recommendations_button);

        // Set an onClickListener for the button to retrieve the last known location and launch the RecommendationsActivity
        getRecommendationsButton.setOnClickListener(view -> getLastLocationForButton(location -> {
            double latitude;
            double longitude;

            if (isSearchLocationSet) {
                latitude = searchedLatitude;
                longitude = searchedLongitude;
            } else {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
            // Create an intent for the RecommendationsActivity
            Intent intent = new Intent(MapsActivity.this, RecommendationsActivity.class);

            // Pass the latitude and longitude values to the RecommendationsActivity
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);

            // Start the RecommendationsActivity
            startActivity(intent);
        }));
        
        //code to navigate bottom navbar
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.maps);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.maps:
                        return true;
                    case R.id.addLocation:
                        startActivity(new Intent(getApplicationContext(), newLocations.class));
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

                }
                return false;
            }
        });
        //end of OnCreate function
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady (GoogleMap googleMap){
        mMap = googleMap;

                //LatLng sydney = new LatLng(-34, 151);
                //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                //BELOW IS CURRENT LOCATION CODE
                //check if location permission granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //if granted, access user location
            enableUserLocation();
            zoomToUserLocation();
        } else {
                //if not granted
            askLocationPermission();
        }
    } //end of OnMapReady function

    @Override
    public void onStart () {
        super.onStart();
                // check if permission granted by user
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //if granted, get previous location
            MapsActivity.getLastLocation(); //made static, may make changes later
        } else {
                    //if not granted, ask for permission from user
            askLocationPermission();
        }
    } //end of on start function

    public static void getLastLocation () {
                //use FusedLocationProviderClient to get last location, task returns location
                //How it works: location gotten from .getLastLocation() is cached location by other applications
        @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

                //add listeners to task (watch video to learn difference between listeners)
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                            //we have a location
                    Log.d(TAG, "onSuccess: " + location.toString());
                    Log.d(TAG, "onSuccess: " + location.getLatitude());
                    Log.d(TAG, "onSuccess: " + location.getLongitude());
                    Log.d(TAG, "onSuccess: " + location.getTime());

                } else {
                    Log.d(TAG, "onFailure: Location was null...");
                }
            }
        });  //end of AddonSuccessListener function

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }); //end of on failure listener function

    } //end of get last location function

    private void askLocationPermission () {
                //check again if permission already granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //show dialogue to user explaining purpose of asking permission if granted before
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d(TAG, "AskLocationPermission: ask permission dialogue...");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        } else {
                    //if not granted, ask for permission from user
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
        }
    } //end of ask location permission


    //check if user granted permission, if yes grant last location
    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); //added, may remove
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //if permission granted, get previous and current location
                getLastLocation();
                enableUserLocation();
                zoomToUserLocation();
            } else {
                        //if permission not granted, show dialogue that permission not granted
            }
        }
    } //end of on request permission result

    @SuppressLint("MissingPermission") //already asked permission in onRequestPermissionsResult
    private void enableUserLocation () {
        mMap.setMyLocationEnabled(true);
    }
    private void zoomToUserLocation() {
        @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Check if the location object is not null before accessing its properties
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                } else {
                    // Handle the case when the location object is null, e.g., show a message to the user
                    Toast.makeText(MapsActivity.this, "Unable to get current location. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    } //end of zoom to user location

    private void getLastLocationForButton(LocationHandler locationHandler) {
        @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(locationHandler::onLocationReceived);
    }

    private interface LocationHandler {
        void onLocationReceived(Location location);
    }

} //end of map activity class


