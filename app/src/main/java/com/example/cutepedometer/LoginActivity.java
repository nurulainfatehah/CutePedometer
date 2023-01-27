package com.example.cutepedometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseUser;


    public class LoginActivity extends AppCompatActivity {


        EditText email, password;
        Button btnLogin;
        DBHelper myDB;
        public static final String MyPREFERENCES = "MyPrefs" ;
        SharedPreferences sharedpreferences;
        TextView forgotTxtView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //for changing status bar icon color
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            setContentView(R.layout.activity_login);

            email = (EditText) findViewById(R.id.editTextEmail);
            password = (EditText) findViewById(R.id.editTextPassword);

            btnLogin = (Button) findViewById(R.id.cirLoginButton);

            myDB = new DBHelper(this);

            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mail = email.getText().toString();
                    String pass = password.getText().toString();
                    SharedPreferences.Editor editor = sharedpreferences.edit();


                    if (mail.equals("") || pass.equals("")) {
                        Toast.makeText(LoginActivity.this, "Please Enter the Credentials.", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean result = myDB.checkemailPass(mail, pass);

                        if (result == true) {
                            editor.putString(String.valueOf(email), mail);
                            editor.putString(String.valueOf(password), pass);
                            editor.commit();

                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();


                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


        }

        @Override
        protected void onResume() {
            super.onResume();

            // Fetching the stored data
            // from the SharedPreference

            String s1 = sharedpreferences.getString("email", "");
            String s2 = sharedpreferences.getString("password", "");

            // Setting the fetched data
            // in the EditTexts
            email.setText(s1);
            password.setText(s2);
        }

        @Override
        protected void onPause() {
            super.onPause();

            // Creating a shared pref object
            // with a file name "MySharedPref"
            // in private mode
            SharedPreferences.Editor myEdit = sharedpreferences.edit();

            // write all the data entered by the user in SharedPreference and apply
            myEdit.putString("email", email.getText().toString());
            myEdit.putString("password", password.getText().toString());
            myEdit.apply();
        }

        public void onLoginClick(View view) {
            startActivity(new Intent(this, RegisterActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        }

    }