package com.example.fcctut;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class AutoLoginLogoPage extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auto_login_logo_page);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
//                boolean hasLoggedIn=sharedPreferences.getBoolean("hasLoggedin",false);
//
//                if(hasLoggedIn) {
//                    Intent intent = new Intent(AutoLoginLogoPage.this, ProfileActivity2.class);
//                    startActivity(intent);
//                    finish();
//                }else {
//                    Intent intent = new Intent(AutoLoginLogoPage.this, LoginActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        },SPLASH_TIME_OUT);
//    }
    }
}