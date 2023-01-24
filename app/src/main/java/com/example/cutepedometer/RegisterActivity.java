package com.example.cutepedometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText name,email,phone,password, repassword;
    Button btnRegister;
    DBHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.editTextName);
        email = (EditText)findViewById(R.id.editTextEmail);
        phone = (EditText)findViewById(R.id.editTextMobile);
        password = (EditText)findViewById(R.id.editTextPassword);
        repassword = (EditText)findViewById(R.id.editTextRePassword);

        btnRegister = (Button)findViewById(R.id.cirRegisterButton);

        myDB = new DBHelper(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = name.getText().toString();
                String mail = email.getText().toString();
                String contact = phone.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if(fname.equals("") ||mail.equals("") ||contact.equals("") ||pass.equals("")||repass.equals("") )
                {
                    Toast.makeText(RegisterActivity.this, "Please Fill All The Fields!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(pass.equals(repass))
                    {
                        Boolean validUser = myDB.checkemail(mail);

                        if(validUser == false)
                        {
                            Boolean regResult = myDB.insertData(fname,mail,contact,pass);
                            if(regResult == true)
                            {
                                Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        else
                        {
                            Toast.makeText(RegisterActivity.this, "User already exists. \nPlease Sign In.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Password does not Match.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        changeStatusBarColor();
    }

    public void changeStatusBarColor()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onBackClick(View view) {
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,R.anim.stay);
    }
}