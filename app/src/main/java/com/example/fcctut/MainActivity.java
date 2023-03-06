package com.example.fcctut;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView; //import textview from widget package into java file. to get classes for UI elements

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //setting to activity main file this java class related to activity main layout file.

    }

    public void onBtnClick (View view) {
        //class obj_name
        TextView txtFirstName = findViewById(R.id.firstname); //this is to find diff views/diff UIs elements from yr layout files
        //R stands for resources (our static files in our project) txtMessage is the id in our layout xml file.
        TextView txtLastName = findViewById(R.id.lastname);
        TextView txtEmail = findViewById(R.id.email);

        EditText editEmail = findViewById(R.id.editemail);
        EditText edtLastName = findViewById(R.id.editlastname);
        EditText edtFirstName = findViewById(R.id.editFirstName);

        txtFirstName.setText("First Name: "+edtFirstName.getText().toString() );
        txtLastName.setText("Last Name: "+edtLastName.getText().toString());
        txtEmail.setText("Email: "+editEmail.getText().toString());
    }


}