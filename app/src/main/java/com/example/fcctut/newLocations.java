// Import necessary classes
package com.example.fcctut;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;

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


    private PlacesClient placesClient;
    private AutocompleteSessionToken sessionToken;
    private AddPlaces.PlacesAdapter adapter;
    private EditText edtSearch;
    private ProgressBar progressBar;
    private BottomNavigationView bottomNavigationView;


    // onCreate method is called when the activity is created

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the XML layout file (activity_new_locations)
        setContentView(R.layout.activity_new_locations);


//        //code to navigate bottom navbar
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.addLocation);
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
//                    case R.id.addPlacesWorking:
//                        return true;
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
        });// end of Bottom NavBar code
    } //end of OnCreate

    //start of searchplaces function
    private void searchPlaces() {
        progressBar.setVisibility(View.VISIBLE);
        final LocationBias bias = RectangularBounds.newInstance(
                new LatLng(1.34342, 103.95304),
                new LatLng(1.44877, 103.72512)
        );
        final FindAutocompletePredictionsRequest newRequest = FindAutocompletePredictionsRequest
                .builder()
                .setSessionToken(sessionToken)
                //.setTypesFilter(TypeFilter.ESTABLISHMENT)
                .setQuery(edtSearch.getText().toString())
                .setLocationBias(bias)
                .setCountries("SG")
                .build();
        placesClient.findAutocompletePredictions(newRequest).addOnSuccessListener(new OnSuccessListener<FindAutocompletePredictionsResponse>() {
            @Override
            public void onSuccess(FindAutocompletePredictionsResponse findAutocompletePredictionsResponse) {
                List<AutocompletePrediction> predictions = findAutocompletePredictionsResponse.getAutocompletePredictions();
                adapter.setPredictions(predictions);
                progressBar.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    Log.e("Places activity", "place not found" + apiException.getStatusCode());
                }
            }
        }); //end of add on failurelistener function

    } //end of searchPlaces function


    //start of detail place function
    private void detailPlace(String placeId) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading..");
        progressDialog.show();

        final List<com.google.android.libraries.places.api.model.Place.Field> placeFields = Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID, com.google.android.libraries.places.api.model.Place.Field.NAME, com.google.android.libraries.places.api.model.Place.Field.LAT_LNG);
        final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                progressDialog.dismiss();
                com.google.android.libraries.places.api.model.Place place = fetchPlaceResponse.getPlace();
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    Toast.makeText(newLocations.this, "Latlng" + latLng, Toast.LENGTH_SHORT).show();
                }
            }  //end of onsuccess function
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                if (e instanceof ApiException) {
                    final ApiException apiException = (ApiException) e;
                    Log.e("add places Activity", "Place not found: "+e.getMessage());
                    final int statusCode = apiException.getStatusCode();
                }
            }
        }); //end of addonfailure function
    } //end of detail places function

    //start of a private static class
    private static class PlacesAdapter extends BaseAdapter {
        private final List<AutocompletePrediction> predictions = new ArrayList<>();
        private final Context context;

        public PlacesAdapter(Context context) {
            this.context = context;
        }

        public void setPredictions(List<AutocompletePrediction> predictions) {
            this.predictions.clear();
            this.predictions.addAll(predictions);
            notifyDataSetChanged();
        }


        @Override
        public int getCount() {
            return predictions.size();
        }

        @Override
        public Object getItem(int position) {
            return predictions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            View v = LayoutInflater.from(context).inflate(R.layout.add_places_2, viewGroup, false);
            TextView txtShortAddress = v.findViewById(R.id.txtShortaddress);
            TextView txtLongAddress = v.findViewById(R.id.txtLongaddress);

            txtShortAddress.setText(predictions.get(position).getPrimaryText(null));
            txtLongAddress.setText(predictions.get(position).getSecondaryText(null));
            return v;
        }
    } //end of places adapter class static

    //start of attempt to parse data
//    private void detailPlace(String placeId) {
//        ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("loading..");
//        progressDialog.show();
//
//        //choose fields to fetch in fetch request, then put in array
//        final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.OPENING_HOURS, Place.Field.BUSINESS_STATUS);
//        //initialise fetch request
//        final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
//        //have client listen for request to generate response


//        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
//            @SuppressLint("MissingPermission") //temporary, might have to ask for permissions later
//            @Override
//            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
//                progressDialog.dismiss();
//                //initialise place object to retrieve place data
//                Place place = fetchPlaceResponse.getPlace();
//
//                //create variables for data to retrieve
//                String openingHours = String.valueOf(place.getOpeningHours());
//                String weeklyOpeningHoursList = String.valueOf(place.getOpeningHours().getWeekdayText());
//                String rating = String.valueOf(place.getRating());
//                String phoneNumber = place.getPhoneNumber();
//                LatLng latLng = place.getLatLng();
//                ItineraryList.add(place.getName());  //check if added
//
//
//
//                //below code is to get json format of response, which OkHTTP makes difficult. Can explore decoders if needed. Else, delete this code.
////                OkHttpClient client = new OkHttpClient().newBuilder()
////                        .build();
////                MediaType mediaType = MediaType.parse("text/plain");
////                RequestBody body = RequestBody.create(mediaType, "");
////                String urlInput = "https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJN1t_tDeuEmsRUsoyG83frY4&fields=name%2Crating%2Cformatted_phone_number&key="+ apiKey;
////                Request request = new Request.Builder()
////                        .url(urlInput)
////                        .method("GET", body)
////                        .build();
////                try {
////                    Response response = client.newCall(request).execute();
////                    if(!response.isSuccessful()) {
////                        Log.i("Response code", " " + response.code());
////                    }
////
////                    Log.i("Response code", response.code() + " ");
////                    String results = response.body().toString();
////
////                } catch (IOException e) {
////                    throw new RuntimeException(e);
////                }
//
//                //Display data as notification
//                if (weeklyOpeningHoursList != null) {
//                    //as example: get opening hours
//                    Toast.makeText(AddPlaces.this, "Weekly Opening Hours" + weeklyOpeningHoursList, Toast.LENGTH_LONG).show();
////                    Toast.makeText(AddPlaces.this, "json:", Toast.LENGTH_LONG).show();
//
//
//                }
//
//                //add button
//
////                public void onAddToItineraryButtonClick(View v){
////                    //initialise itinerary listview
////                    ListView ItineraryList = findViewById(R.id.placesListView);
////
////                    //create arraylist to pass into listview containing place details
////                    ArrayList<String> placeDetails = new ArrayList<>();
////                    placeDetails.add(openingHours);
////                    placeDetails.add(rating);
////                    placeDetails.add(phoneNumber);
////
////                    //create adapter to fetch arraylist into listview
////                    ArrayAdapter<String> placeDetailsAdapter = new ArrayAdapter<>(
////                            this,
////                            android.R.layout.simple_list_item_1,
////                            placeDetails
////                    );
////
////                }
//
//                class ListViewDemo extends ListActivity {
//                    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
//                    ArrayList<String> listItems=new ArrayList<String>();
//
//                    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
//                    ArrayAdapter<String> adapter;
//
//                    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
//                    int clickCounter=0;
//
//                    @Override
//                    public void onCreate(Bundle icicle) {
//                        super.onCreate(icicle);
//                        setContentView(R.layout.activity_iternary);
//                        adapter=new ArrayAdapter<String>(this,
//                                android.R.layout.simple_list_item_1,
//                                listItems);
//                        setListAdapter(adapter);
//                    }
//
//                    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
//                    public void addItems(String v) {
//                        listItems.add("Clicked : "+clickCounter++);
//                        adapter.notifyDataSetChanged();
//                    }
//
//                }
//
//                ListViewDemo addedPlacesList = new ListViewDemo();
//                addedPlacesList.addItems(weeklyOpeningHoursList);
//
//                //initialise itinerary list view
//                ListView ItineraryListview = findViewById(R.id.placesListView);
//
//                //add to itinerary
//                Button btnAddToItinerary = findViewById(R.id.btnAddToItinerary);
//                btnAddToItinerary.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        addedPlacesList.addItems(weeklyOpeningHoursList);
//
//                    }
//                });
//                //doesnt work
//
//
//
//
//
//
//
//            }  //end of onsuccess function
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressDialog.dismiss();
//                if (e instanceof ApiException) {
//                    final ApiException apiException = (ApiException) e;
//                    Log.e("add places Activity", "Place not found: "+e.getMessage());
//                    final int statusCode = apiException.getStatusCode();
//                }
//            }
//        }); //end of addonfailure function
//    } //end of detail places function



}

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

