package com.example.fcctut;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextView signuptextview;
    Button loginbutton;
    Button signingooglebutton;
    GoogleSignInOptions googleSignInOptions;
    ProgressDialog progressDialog;
    GoogleSignInClient googleSignInClient;

    private static final int RC_SIGN_IN = 1;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        BottomNavigationView bottomNavigationView;


        signingooglebutton = findViewById(R.id.signingooglebutton);

        loginbutton = findViewById(R.id.loginbutton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ProfileActivity2.class);
                startActivity(intent);
            }
        });

        progressDialog = new ProgressDialog(this);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();


//        if(mUser!=null){
//            Intent intent = new Intent(LoginActivity.this,ProfileActivity2.class);
//            startActivity(intent);
//        }

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        signingooglebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GoogleLoginActivity.class);
                startActivity(intent);

                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
            }
        });

        
    }//end of OnCreate


}//end of login activity class


//    private void ProfileActivity2(){
//
//        finish();
//        Intent intent = new Intent(getApplicationContext(),ProfileActivity2.class);
//        startActivity(intent);
//    }




//        TextView signup = (TextView) findViewById(R.id.signuptextview);
//        signup.setOnClickListener(
//                new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v){
//                        Intent intent = new Intent (LoginActivity.this, SignUpPage.class);
//                        startActivity(intent);
//                    }
//                }
//        );
//    private void SignIn() {
//        Intent signInIntent = googleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent,RC_SIGN_IN);
//    }

//
//




 //end of login activity class

