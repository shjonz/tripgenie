package com.example.fcctut;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class showItinerary extends AppCompatActivity {
    RecyclerView itineraryList;
    TextView txtViewStartDate;
    TextView txtViewEndDate;
    RecyclerView recyclerViewDates;
    private BottomNavigationView bottomNavigationView;
    private Button homebutton;
    private Spinner dropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_itinerary);


        dropdown = findViewById(R.id.datedropdown);

        //TODO update filemanager filename

        // Create an ArrayAdapter using a string array resource and a default spinner layout
        ArrayAdapter<CharSequence> Adapter = ArrayAdapter.createFromResource(this, R.array.day_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        dropdown.setAdapter(Adapter);


        homebutton=findViewById(R.id.homeButton);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(showItinerary.this,ProfileActivity2.class);
                startActivity(intent);
            }
        });

        itineraryList = findViewById(R.id.itineraryList);
        txtViewStartDate = findViewById(R.id.txtViewStartDate);
        txtViewEndDate = findViewById(R.id.txtViewEndDate);

        // TODO: dynamically update filename
        Trip t = FileManager.getTrip(showItinerary.this, "final.json");
       // TODO: dynamically update filename
       SharedPreferences sharedPref = getSharedPreferences("fileNameSaver", Context.MODE_PRIVATE);
       String filename = sharedPref.getString("currentFile", "");
      // Trip t = FileManager.getTrip(showItinerary.this, filename);
//        t.saveToTripFile(showItinerary.this, "testing.json");
//        FileManager.saveTrip();

        txtViewStartDate.setText(t.startDate);
        txtViewEndDate.setText(t.endDate);

        //Log.d("testing Trip", t.days.get(0).toString());

        // Lookup the recyclerview in activity layout
        RecyclerView rvPlaces = (RecyclerView) findViewById(R.id.itineraryList);

        // Display on recycler view
        //ArrayList<Place> places = t.days.get(0);

        // Create adapter passing in the sample user data
        RecyclerViewDatesItemAdapter adapterDate = new RecyclerViewDatesItemAdapter(t.days);
        // Attach the adapter to the recyclerview to populate items
//        rvDates.setAdapter(adapterDate);
//        // Set layout manager to position the items
//        rvDates.setLayoutManager(new LinearLayoutManager(this));
        String state=dropdown.getSelectedItem().toString();
        String numericString =state.replaceAll("[^0-9]", ""); // Remove non-numeric characters
        int number = Integer.parseInt(numericString);
        ArrayList<Place> places = t.days.get( number - 1 );

//        places.add(new Place("id", "name", "address", 0.0)); //TODO: change this to take dynamic data
//        places.add(new Place("id1", "name1", "address1", 1.0));
        // Create adapter passing in the sample user data
        ItineraryListViewAdapter adapter = new ItineraryListViewAdapter(places);
        // Attach the adapter to the recyclerview to populate items
        rvPlaces.setAdapter(adapter);
        // Set layout manager to position the items
        rvPlaces.setLayoutManager(new LinearLayoutManager(this));

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Extract the day number from the selected item's text
                String numericString = selectedItem.replaceAll("[^0-9]", "");
                int dayNumber = Integer.parseInt(numericString);

                // Get the ArrayList of places for the selected day
                ArrayList<Place> placesForSelectedDay = t.days.get(dayNumber - 1);

                // Create adapter passing in the places for the selected day
                ItineraryListViewAdapter adapter = new ItineraryListViewAdapter(placesForSelectedDay);

                // Attach the adapter to the RecyclerView to populate items
                rvPlaces.setAdapter(adapter);

                // Set layout manager to position the items
               rvPlaces.setLayoutManager(new LinearLayoutManager(showItinerary.this));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        //RecyclerView rvPlaces = (RecyclerView) findViewById(R.id.itineraryList);
        //RecyclerViewDatesItemAdapter adapterDate = new RecyclerViewDatesItemAdapter(t.days);

//        String state = dropdown.getSelectedItem().toString();
//        String numericString = state.replaceAll("[^0-9]", ""); // Remove non-numeric characters
//        int number = Integer.parseInt(numericString);
//        ArrayList<Place> places = t.days.get(number - 1);

        //ItineraryListViewAdapter adapter = new ItineraryListViewAdapter(places);
        //rvPlaces.setAdapter(adapter);

        //rvPlaces.setLayoutManager(new LinearLayoutManager(this));

        //Adapter.updateData(selectedItem);




//        // Lookup the recyclerview in activity layout
//        RecyclerView rvDates = (RecyclerView) findViewById(R.id.recyclerViewDates);




        //code for bottom NavBar
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //get id of item in navbar to switch to
                switch (item.getItemId()) {
                    case R.id.maps:
//                        Toast.makeText(MainActivity.this,"Loading Maps",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.addLocation:
                        startActivity(new Intent(getApplicationContext(), newLocations.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.itinerary:

                        return true;
                    case R.id.savedLocations:
                        startActivity(new Intent(getApplicationContext(), SavedLocations.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        }); // end of code for bottom NavBar
    } // end of OnCreate
} // end of showItinerary Class

