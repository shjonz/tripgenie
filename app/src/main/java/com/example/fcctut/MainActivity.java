package com.example.fcctut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView; //import textview from widget package into java file. to get classes for UI elements

import java.sql.Timestamp;

public class MainActivity extends AppCompatActivity {
    private Button btn_tomaps;
    private Button btn_toPlanTrips;
    TextView textViewMsg;

    final String node = "current_msg";
    DatabaseReference mRootDatabaseRef; //reference to database
    DatabaseReference mNodeRef; //node reference

    private Button btn_tosavedplaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //setting to activity main file this java class related to activity main layout file.
        //logcat statement

        Log.d("Pokemon", "I am in onCreate "); //string that acts as a tag,
        btn_tomaps = (Button) findViewById(R.id.btn_tomaps);
        btn_tomaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapsActivity();
            }
        });

        //button to go to planTrip page (dylans page)
        btn_toPlanTrips = (Button) findViewById(R.id.btn_toPlanTrip);
        btn_toPlanTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPlanTripActivity();
            }
        }); //end of onclicklistener


        //button to go to saved places list view
        btn_tosavedplaces = (Button) findViewById(R.id.btn_tosavedplaces);
        btn_tosavedplaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSavedPlacesActivity();
            }
        }); //end of setonclick listener

        textViewMsg = findViewById(R.id.textViewdb); //look up layout of android
        mRootDatabaseRef = FirebaseDatabase.getInstance().getReference(); //get reference to database, root element of db
        mNodeRef = mRootDatabaseRef.child(node); //child element of root element.

        mNodeRef.addValueEventListener(new ValueEventListener() { //when db updates in real time, so happens synchronously, event listener
            @Override
            //objects will be registered to mNodeRef, when sth happens, data will be updated or cancelled.
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String change = snapshot.getValue(String.class);
                textViewMsg.setText(change);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); //end of add value event listener

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        mNodeRef.setValue(timestamp.toString());
    } // end of on create function




        public void onBtnClick(View view) {
                //class obj_name
            TextView txtFirstName = findViewById(R.id.firstname); //this is to find diff views/diff UIs elements from yr layout files
                //R stands for resources (our static files in our project) txtMessage is the id in our layout xml file.
            TextView txtLastName = findViewById(R.id.lastname);
            TextView txtEmail = findViewById(R.id.email);

            EditText editEmail = findViewById(R.id.editemail);
            EditText edtLastName = findViewById(R.id.editlastname);
            EditText edtFirstName = findViewById(R.id.editFirstName);

            txtFirstName.setText("First Name: " + edtFirstName.getText().toString());
            txtLastName.setText("Last Name: " + edtLastName.getText().toString());
            txtEmail.setText("Email: " + editEmail.getText().toString());
        }


        public void openMapsActivity() {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);

        }


        public void openSavedPlacesActivity() {
//                //Intent intent = new Intent(this, IternaryActivity.class);
        }

    public void openPlanTripActivity() { //opens dylans page PlanTrip page.
        Intent intent = new Intent(this, PlanTrip.class);
        startActivity(intent);
    }


} //end of MainActivity class
