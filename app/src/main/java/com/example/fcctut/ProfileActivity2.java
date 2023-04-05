package com.example.fcctut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.jar.Attributes;

public class ProfileActivity2 extends AppCompatActivity {
    ImageButton androidImageButton;
    ImageView profilephoto;
    Button logoutbutton;

    Uri url;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    SharedPreferences sharedPreferences;

    Button addtripbutton;

    //for storing into database
    TextView name;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);//setting to activity main file this java class related to activity main layout file.

        //storing username into database
//        name=findViewById(R.id.name);
//        //get instance of firebase database
//        databaseReference=firebaseDatabase.getReference("User");
//        //Initialize class variable
//        user= new User();

        //to access plan trip page
        addtripbutton=findViewById(R.id.addtripbutton);
        addtripbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity2.this,PlanTrip.class);
                startActivity(intent);
            }
        });//end of access plan trip page

        //to access edit user page
        androidImageButton= (ImageButton) findViewById(R.id.edituserButton);
        androidImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity2.this,EditUserPage.class);
                startActivity(intent);
            }
        });//end of edit user icon

        name=findViewById(R.id.name);
//        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        profilephoto=findViewById(R.id.profilephoto);
        logoutbutton=findViewById(R.id.logoutbutton);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null){
            name.setText(account.getDisplayName());
            profilephoto.setImageURI(account.getPhotoUrl());
        }
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signout();
            }
        });
    } //end of onCreate function

    public void showUserData(){
        Intent intent = getIntent();

        String nameUser=intent.getStringExtra("name");

        name.setText(nameUser);
    }

//    private void addDatatoFirebase(String name){
//        user.setName(name);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                databaseReference.setValue(user);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void Signout() {
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();

            }
        });
    }

    //            String username = name.getText().toString();
//
//            User user = new User(username);
//            FirebaseDatabase.getInstance().getReference("Users")
//                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                finish();
//                            }
//                        }
//                    });




} //end of ProfileActivity class

//    private void uploadData(String username){
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        User user = new User(username);
//        FirebaseDatabase.getInstance().getReference("Users")
//                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()){
//                            String uid = user.getUid();
//                            String name = user.getDisplayName();
//                            String email = user.getEmail();
//
//                            FirebaseDatabase database = FirebaseDatabase.getInstance();
//                            DatabaseReference usersRef = database.getReference("users");
//
//                            DatabaseReference newUserRef = usersRef.child(uid);
//                            newUserRef.child("name").setValue(name);
//                            newUserRef.child("email").setValue(email);
//                            finish();
//                        }
//                    }
//                });
//
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
