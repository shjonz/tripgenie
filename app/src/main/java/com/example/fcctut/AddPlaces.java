package com.example.fcctut;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.LocationBias;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddPlaces extends AppCompatActivity {
    private PlacesClient placesClient;
    private AutocompleteSessionToken sessionToken;
    private PlacesAdapter adapter;
    private EditText edtSearch;
    private ProgressBar progressBar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);

        String apiKey = BuildConfig.MAPS_API_KEY; //places api key
        if (!Places.isInitialized()) {
            Places.initialize(this, apiKey);
        }
        placesClient = Places.createClient(this);
        sessionToken = AutocompleteSessionToken.newInstance();

        edtSearch = findViewById(R.id.searchSavedPlaces);
        progressBar = findViewById(R.id.progressBarSearchplaces);
        ListView listPlaces = findViewById(R.id.searchListViewPlaces);


        progressBar.setVisibility(View.GONE);
        adapter = new PlacesAdapter(this);
        listPlaces.setAdapter(adapter);
        listPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (adapter.getCount() > 0) {
                    detailPlace(adapter.predictions.get(position).getPlaceId());
                }
            }
        }); //setonitemclicklistener function
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(edtSearch.length() > 0 ) {
                        searchPlaces();
                    }
                }
                return false;
            }
        }); //end of setoneditoractionlistener


//        bottomNavigationView =findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setSelectedItemId(R.id.addLocation);
//
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                //get id of item in navbar to switch to
//                int itemId = item.getItemId();
//                if (itemId==0){
//                    Toast.makeText(AddPlaces.this, "Please add inputs", Toast.LENGTH_LONG).show();
//                }
//                switch (item.getItemId()) {
//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.maps:
//                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//
//                    case R.id.addLocation:
//                        return true;
//
//                    case R.id.itinerary:
//                        startActivity(new Intent(getApplicationContext(), showItinerary.class));
//                        overridePendingTransition(0, 0);
//                        return true;
//
//                }
//                return false;
//            }
//        });
    } //end of oncreate function

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

        final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse fetchPlaceResponse) {
                progressDialog.dismiss();
                Place place = fetchPlaceResponse.getPlace();
                LatLng latLng = place.getLatLng();
                if (latLng != null) {
                    Toast.makeText(AddPlaces.this, "Latlng" + latLng, Toast.LENGTH_SHORT).show();
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
    static class PlacesAdapter extends BaseAdapter {
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

} //end of addplaces class