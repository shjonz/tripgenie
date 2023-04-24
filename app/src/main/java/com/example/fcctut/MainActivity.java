package com.example.fcctut;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fcctut.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;

public class MainActivity extends AppCompatActivity {
   private Button btn_tomaps;
   private Button btn_toPlanTrips;
   TextView textViewMsg;

   final String node = "current_msg";
   DatabaseReference mRootDatabaseRef; //reference to database
   DatabaseReference mNodeRef; //node reference

   private ActivityMainBinding binding; //for bottom navar, to interact with views
   private BottomNavigationView bottomNavigationView;



   private Button btn_tosavedplaces;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main); //setting to activity main file this java class related to activity main layout file.


   } // end of on create function




}

