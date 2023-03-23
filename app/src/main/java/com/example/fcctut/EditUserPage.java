package com.example.fcctut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditUserPage extends AppCompatActivity {
    Toolbar toolbar;
    EditText editTextChangeUsername;
    Button buttonBacktoProfileActivity;
    public final static String USERNAME="USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_page);

        editTextChangeUsername = findViewById(R.id.editTextChangeUsername);
        buttonBacktoProfileActivity = findViewById(R.id.buttonBacktoProfileActivity);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        buttonBacktoProfileActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextChangeUsername.getText().toString();

                if (username.isEmpty()) {
                    Toast.makeText(EditUserPage.this, R.string.empty_edit_text,
                            Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(EditUserPage.this,
                            ProfileActivity2.class);
                    intent.putExtra(USERNAME, username);
                    startActivity(intent);
                }

            }
        });
    }


    @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                finish();
            }
            return super.onOptionsItemSelected(item);
        }








}