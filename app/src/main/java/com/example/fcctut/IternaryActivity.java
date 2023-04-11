package com.example.fcctut;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class IternaryActivity extends AppCompatActivity {
    String savedPlaceslist[] = {"savedplace1", "savedplace2"}; //grab data
    //int savedPlacesimages[] = {R.drawable. , R.drawable. };
    ListView listView;
    private Button btn_toaddplaces;
    private BottomNavigationView bottomNavigationView;

    private Button homebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iternary);


        btn_toaddplaces = (Button) findViewById(R.id.btn_to_searchforplaces);
        btn_toaddplaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPlacesActivity();
            }
        });

        //link listview to xml file
        listView = (ListView) findViewById(R.id.placesListView);
        PlacesBaseAdapter customBaseAdapter = new PlacesBaseAdapter(getApplicationContext(), savedPlaceslist);
        listView.setAdapter(customBaseAdapter);

        //code to navigate bottom navbar
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.itinerary);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.maps:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
//                    case R.id.addLocation:
//                        startActivity(new Intent(getApplicationContext(), AddPlaces.class));
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.itinerary:
                        return true;
//                    case R.id.addPlacesWorking:
//                        startActivity(new Intent(getApplicationContext(), AddPlacesWorking.class));
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.addLocation:
                        startActivity(new Intent(getApplicationContext(), newLocations.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    } //end of oncreate function

    //go to search places page
    public void AddPlacesActivity() {
        Intent intent = new Intent(this, AddPlaces.class);
        startActivity(intent);
    }
} //end of iternrary activity class