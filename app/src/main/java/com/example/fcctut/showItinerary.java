package com.example.fcctut;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class showItinerary extends AppCompatActivity {
    ListView itineraryList;
    private BottomNavigationView bottomNavigationView;

    private Button homebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_itinerary);

        homebutton=findViewById(R.id.HomeButton);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(showItinerary.this,ProfileActivity2.class);
                startActivity(intent);
            }
        });

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

        //code for bottom NavBar
        bottomNavigationView =findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                //get id of item in navbar to switch to
//                int itemId = item.getItemId();
////                System.out.println("Id of item clicked: "+itemId);
////                System.out.println(R.id.maps+" is the maps R.id");
//                if (itemId==0){
//                    Toast.makeText(showItinerary.this, "Please add inputs", Toast.LENGTH_LONG).show();
//                }
                switch (item.getItemId()) {
//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(), ProfileActivity2.class));
//                        overridePendingTransition(0, 0);
//                        return true;
                    case R.id.maps:
//                        Toast.makeText(MainActivity.this,"Loading Maps",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.addLocation:
                        startActivity(new Intent(getApplicationContext(), newLocations.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.savedLocations:
                        startActivity(new Intent(getApplicationContext(), SavedLocations.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.itinerary:
                        return true;

                }
                return false;
            }
        }); // end of code for bottom NavBar
    } // end of OnCreate
} // end of showItinerary Class