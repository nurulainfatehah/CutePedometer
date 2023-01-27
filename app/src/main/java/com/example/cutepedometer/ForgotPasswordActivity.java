package com.example.cutepedometer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ForgotPasswordActivity  extends AppCompatActivity {

    EditText name, email, phone;
    Button btnVerify;
    DBHelper myDB;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_forgot_password);

        name = (EditText)findViewById(R.id.editTextName);
        email = (EditText)findViewById(R.id.editTextEmail);
        phone = (EditText)findViewById(R.id.editTextMobile);
        btnVerify = (Button)findViewById(R.id.cirVerifyButton);

        myDB = new DBHelper(this);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btnVerify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String verifyName = name.getText().toString();
                String verifyMail = email.getText().toString();
                String verifyPhone = phone.getText().toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();

                if (verifyName.equals("") || verifyMail.equals("")) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter Registered Name.", Toast.LENGTH_SHORT).show();
                } else if(verifyMail.equals("")){
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter Registered E-mail.", Toast.LENGTH_SHORT).show();
                } else if(verifyPhone.equals((""))){
                    Toast.makeText(ForgotPasswordActivity.this, "Please Enter Registered Phone.", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean result = myDB.verifyForgotPassword(verifyName, verifyMail, verifyPhone);

                    if(result == true){
                        editor.putString(String.valueOf(name), verifyName);
                        editor.putString(String.valueOf(email), verifyMail);
                        editor.putString(String.valueOf(phone), verifyPhone);
                        editor.commit();

                        btnVerify.setVisibility(View.INVISIBLE);

                        /*Toast.makeText(ForgotPasswordActivity.this, "Yey, we have found your record. Enter your new password below", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), CounterActivity.class);
                        startActivity(intent);*/

                    }else{
                        Toast.makeText(ForgotPasswordActivity.this, "Sorry, we couldn't find any users record with the information entered.", Toast.LENGTH_SHORT).show();

                    }
                }

            }

        });
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
    }


}
