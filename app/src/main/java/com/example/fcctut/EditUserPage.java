package com.example.fcctut;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.internal.Util;

import java.util.HashMap;

public class EditUserPage extends AppCompatActivity {

    Toolbar toolbar;

    ImageView uploadImage;
    EditText editTextChangeUsername;
    Button saveButton;
    String imageURL;

    Bitmap bitmap;
    Uri uri;
    public final static String USERNAME = "USERNAME";
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_page);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUID = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userUID);



        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //change profile and username
        uploadImage = findViewById(R.id.changeprofilephoto);
        editTextChangeUsername = findViewById(R.id.editTextChangeUsername);
        saveButton = findViewById(R.id.savebutton);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Uri photoUri = data.getData();
                            uploadImage.setImageURI(photoUri);

//                            try {
//                                bitmap = MediaStore.Images.Media.getBitmap(
//                                        EditUserPage.this.getContentResolver(),
//                                        photoUri
//                                );
//
//                            }

                        } else {
                            Toast.makeText(EditUserPage.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                saveData();
                String nameupdate=editTextChangeUsername.getText().toString();
                if(TextUtils.isEmpty(nameupdate)){
                    Intent intent=new Intent(EditUserPage.this,ProfileActivity2.class);
                    startActivity(intent);
                    finish();
                }else{
                    HashMap newusername=new HashMap<>();
                    newusername.put("name",nameupdate);
                    databaseReference.updateChildren(newusername);
                    Intent intent=new Intent(EditUserPage.this,ProfileActivity2.class);
                    startActivity(intent);
                }

//
            }
        });
    }

//    public void saveData() {
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
//                .child(uri.getLastPathSegment());
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(EditUserPage.this);
//        builder.setCancelable(true);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                while (!uriTask.isComplete()) ;
//                Uri urlImage = uriTask.getResult();
//                imageURL = urlImage.toString();
//                uploadData();
//                dialog.dismiss();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                dialog.dismiss();
//            }
//        });
//    }
//
//    public void uploadData() {
//
//        String username = editTextChangeUsername.getText().toString();
//
//        DataClass dataClass = new DataClass(username, imageURL);
//
//        FirebaseDatabase.getInstance().getReference("Users")
//                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            String namefromDb= dataClass.getDataname();
//
//                            Intent intent = new Intent(EditUserPage.this,ProfileActivity2.class);
//
//                            intent.putExtra("name",namefromDb);
//                            startActivity(intent);
//                            Toast.makeText(EditUserPage.this, "Saved", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(EditUserPage.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//
//    }
}

        
        


        //plus button to go back to profile page
//        buttonBacktoProfileActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = editTextChangeUsername.getText().toString();
//
//                if (username.isEmpty()) {
//                    Toast.makeText(EditUserPage.this, R.string.empty_edit_text,
//                            Toast.LENGTH_LONG).show();
//                } else {
//                    Intent intent = new Intent(EditUserPage.this,
//                            ProfileActivity2.class);
//                    intent.putExtra(USERNAME, username);
//                    startActivity(intent);
//                }
//
//            }
//        });
//    }


//    @Override
//        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//            if (item.getItemId() == android.R.id.home) {
//                finish();
//            }
//            return super.onOptionsItemSelected(item);
//        }








