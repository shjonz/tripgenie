package com.example.fcctut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class GoogleLoginActivity extends LoginActivity {
    GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN=1;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME="mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Google Sign In...");
        progressDialog.show();

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);


        Intent signinIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signinIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("username",account.getDisplayName());
//                editor.apply();
            } catch (ApiException e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();

            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            FirebaseUser user = mAuth.getCurrentUser();

                            String name = user.getDisplayName();
                            String email = user.getEmail();

                            FirebaseUser userID = FirebaseAuth.getInstance().getCurrentUser();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference usersRef = database.getReference("users");
                            DatabaseReference newUserRef = usersRef.child(user.getUid());
                            newUserRef.child("name").setValue(name);
                            newUserRef.child("email").setValue(email);


                            progressDialog.dismiss();
                            updateUI(user);
//                            if (user != null) {
//                                String uid = user.getUid();
//                                String name = user.getDisplayName();
//                                String email = user.getEmail();
//
//                                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                                DatabaseReference usersRef = database.getReference("users");
//
//                                DatabaseReference newUserRef = usersRef.child(uid);
//                                newUserRef.child("name").setValue(name);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(GoogleLoginActivity.this, "Authentication failed" , Toast.LENGTH_SHORT).show();
//                            Log.d("google login", task.getException().toString());
                            finish();
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user) {
        Intent intent=new Intent(GoogleLoginActivity.this,ProfileActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
//        Toast.makeText(GoogleLoginActivity.this, ""+.ge, Toast.LENGTH_SHORT).show();

    }
}