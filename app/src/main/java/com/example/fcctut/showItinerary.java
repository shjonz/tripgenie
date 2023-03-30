package com.example.fcctut;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class showItinerary extends AppCompatActivity {
    ListView itineraryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_itinerary);

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
    }
}