package com.example.fcctut;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.w3c.dom.Text;

import java.util.prefs.Preferences;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText inputEmail,inputPassword;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Button loginbutton;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    TextView signuptextview;
    Button signingooglebutton;
    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        BottomNavigationView bottomNavigationView;

        progressDialog=new ProgressDialog(this);
        signuptextview=findViewById(R.id.signuptextview);
        signuptextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LoginActivity.this,SignUpPage.class);
                startActivity(intent);
            }
        });
        // storing email in shared preferences
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.contains("saved_Email")) {
            Intent intent = new Intent(LoginActivity.this, ProfileActivity2.class);
            startActivity(intent);

        } else {
            inputEmail = findViewById(R.id.inputEmail);
            inputPassword = findViewById(R.id.inputPassword);
            loginbutton = findViewById(R.id.loginbutton);
            loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    perforLogin();
                }
            });
        }

        signingooglebutton = findViewById(R.id.signingooglebutton);
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        mAuth= FirebaseAuth.getInstance();
        boolean isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false);

        if (isLoggedIn) {
            googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if (account != null) {
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    sendUserToNextActivity();
                                    Intent intent = new Intent(LoginActivity.this,ProfileActivity2.class);
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(LoginActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
        signingooglebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, GoogleLoginActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            }
        });//end of google sign in
    }//end of OnCreate

    private void perforLogin() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        editor.putString("saved_Email",email);
        editor.putString("saved_password",password);
        editor.commit();

        if (!email.matches(emailPattern))
        {
            inputEmail.setError("Enter Correct Email");
        }else if(password.isEmpty() || password.length()<6)
        {
            inputPassword.setError("Password must be more than 6 characters");
        }else {
            progressDialog.setMessage("Logging in..");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserToNextActivity() {
        Intent intent=new Intent(LoginActivity.this,ProfileActivity2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}//end of login activity class





//    private void SignIn() {
//        Intent signInIntent = googleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent,RC_SIGN_IN);
//    }

//        if(mUser!=null){
//            Intent intent = new Intent(LoginActivity.this,ProfileActivity2.class);
//            startActivity(intent);
//        }




 //end of login activity class
//end of login activity class


