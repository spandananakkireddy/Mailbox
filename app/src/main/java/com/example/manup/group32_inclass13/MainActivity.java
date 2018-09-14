package com.example.manup.group32_inclass13;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText etemail, etpassword;
    Button btnlogin, btnsignup;

  Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            mAuth = FirebaseAuth.getInstance();
            etemail= (EditText) findViewById(R.id.etemail);
            etpassword= (EditText) findViewById(R.id.etpassword);
            btnlogin= (Button) findViewById(R.id.btnlogin);
            btnsignup= (Button) findViewById(R.id.btnnewuser);
            toolbar=(Toolbar) findViewById(R.id.toolbar2);
            setSupportActionBar(toolbar);
            //getSupportActionBar().setTitle("MessageMe!");
        if(mAuth.getCurrentUser() != null)
        {
            Intent intent= new Intent(MainActivity.this,InboxActivity.class);
            startActivity(intent);
            finish();
        }

            btnsignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userLogin();
                }
            });
        }

        private void userLogin() {
            String email= etemail.getText().toString().trim();
            String password = etpassword.getText().toString().trim();

            if(email.equals(""))
            {
                etemail.setError("Email is required");
                etemail.requestFocus();
                return;
            }
            if(password.equals(""))
            {
                etpassword.setError("Password is required");
                etpassword.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                etemail.setError("Invalid email");
                etemail.requestFocus();
                return;
            }
            if(password.length()< 6)
            {
                etpassword.setError("Minimum pass length is 6");
                etpassword.requestFocus();
                return;
            }

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Intent intent = new Intent(MainActivity.this,InboxActivity.class);
                        startActivity(intent);
                        finishAffinity();


                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

