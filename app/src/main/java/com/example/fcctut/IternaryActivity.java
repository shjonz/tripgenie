package com.example.fcctut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class IternaryActivity extends AppCompatActivity {
    String savedPlaceslist[] = {"savedplace1", "savedplace2"}; //grab data
    //int savedPlacesimages[] = {R.drawable. , R.drawable. };
    ListView listView;
    private Button btn_toaddplaces;

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
//        PlacesBaseAdapter customBaseAdapter = new PlacesBaseAdapter(getApplicationContext(), savedPlaceslist);
//        listView.setAdapter(customBaseAdapter);
    }

    //go to search places page
    public void AddPlacesActivity() {
        Intent intent = new Intent(this, AddPlaces.class);
        startActivity(intent);
    }
}