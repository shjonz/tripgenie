package com.example.fcctut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ProfileActivity2 extends AppCompatActivity {
    ImageButton edituserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);//setting to activity main file this java class related to activity main layout file.
        edituserButton=findViewById(R.id.edituserButton);
        edituserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity2.this,
                        EditUserPage.class);
                startActivity(intent);
            }
        });
    } //end of onCreate function
//        captureimage=findViewById(R.id.setting_profile_image);{
//            public void onClick(View V){
//                ImagePicker.Companion.with(imageActivity.this)
//                        .crop()
//                        .maxResultSize(1080,1080)

//    public void addtripbutton(View view){
//        setContentView(R.layout.homepage);
//    }



//    public void onBtnClick (View view) {
//        //class obj_name
//        TextView txtFirstName = findViewById(R.id.firstname); //this is to find diff views/diff UIs elements from yr layout files
//        //R stands for resources (our static files in our project) txtMessage is the id in our layout xml file.
//        TextView txtLastName = findViewById(R.id.lastname);
//        TextView txtEmail = findViewById(R.id.email);
//
//        EditText editEmail = findViewById(R.id.editemail);
//        EditText edtLastName = findViewById(R.id.editlastname);
//        EditText edtFirstName = findViewById(R.id.editFirstName);
//
//        txtFirstName.setText("First Name: "+edtFirstName.getText().toString() );
//        txtLastName.setText("Last Name: "+edtLastName.getText().toString());
//        txtEmail.setText("Email: "+editEmail.getText().toString());
//    }


} //end of ProfileActivity class
