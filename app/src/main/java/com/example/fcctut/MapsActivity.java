package com.example.fcctut;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.location.LocationRequest;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.location.Priority;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.net.PlacesClient;


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

//    public MapsActivity(FusedLocationProviderClient mFusedLocationProviderClient, PlacesClient placesClient, List<AutocompletePrediction> predictionList) {
//        this.mFusedLocationProviderClient = mFusedLocationProviderClient;
//        this.placesClient = placesClient;
//        this.predictionList = predictionList;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("maps", "i am in maps create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); //to view map fragment

        searchView = findViewById(R.id.search_view_location); //this is to get search view location that user entered in the search bar

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this); //this code is to get map fragment for the map to display


        //now below this is gonna be a function that listens for the text user inputs into the search bar to enter location
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList=null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder((MapsActivity.this));
                    try {
                        addressList=geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        }); //end of function for listening to search bar input
        mapFragment.getMapAsync(this); //this will update the map.

        //initialise FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //BELOW IS CURRENT LOCATION CODE
        //check if lcoation permission granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //if granted, access user location
            enableUserLocation();
            zoomToUserLocation();
        } else {
            //if not granted
            askLocationPermission();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // check if permission granted by user
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //if granted, get previous location
            MapsActivity.getLastLocation(); //made static, may make changes later
        } else {
            //if not granted, ask for permission from user
            askLocationPermission();
        }

    }

    public static void getLastLocation() {
        //use FusedLocationProviderClient to get last location, task returns location
        //How it works: location gotten from .getLastLocation() is cached location by other applications
        @SuppressLint("MissingPermission") Task<Location> locationTask =fusedLocationProviderClient.getLastLocation();

        //add listeners to task (watch video to learn difference between listeners)
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    //we have a location
                    Log.d(TAG, "onSuccess: " + location.toString());
                    Log.d(TAG, "onSuccess: " + location.getLatitude());
                    Log.d(TAG, "onSuccess: " + location.getLongitude());
                    Log.d(TAG, "onSuccess: " + location.getTime());

                } else {
                    Log.d(TAG, "onFailure: Location was null...");
                }
            }
        });

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void askLocationPermission() {
        //check again if permission already granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //show dialogue to user explaining purpose of asking permission if granted before
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
                Log.d(TAG, "AskLocationPermission: ask permission dialogue...");
                ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        } else {
            //if not granted, ask for permission from user
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
        }
    }


    }

    public static void getLastLocation() {
        //use FusedLocationProviderClient to get last location, task returns location
        //How it works: location gotten from .getLastLocation() is cached location by other applications
        @SuppressLint("MissingPermission") Task<Location> locationTask =fusedLocationProviderClient.getLastLocation();

        //add listeners to task (watch video to learn difference between listeners)
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    //we have a location
                    Log.d(TAG, "onSuccess: " + location.toString());
                    Log.d(TAG, "onSuccess: " + location.getLatitude());
                    Log.d(TAG, "onSuccess: " + location.getLongitude());
                    Log.d(TAG, "onSuccess: " + location.getTime());

                } else {
                    Log.d(TAG, "onFailure: Location was null...");
                }
            }
        });

        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void askLocationPermission() {
        //check again if permission already granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //show dialogue to user explaining purpose of asking permission if granted before
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
                Log.d(TAG, "AskLocationPermission: ask permission dialogue...");
                ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        } else {
            //if not granted, ask for permission from user
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
        }
    }

    //check if user granted permission, if yes grant last location
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
    }

    @SuppressLint("MissingPermission") //already asked permission in onRequestPermissionsResult
    private void enableUserLocation() {
        mMap.setMyLocationEnabled(true);
    }

    private void zoomToUserLocation() {
        @SuppressLint("MissingPermission") Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
            }
        });
    }

}