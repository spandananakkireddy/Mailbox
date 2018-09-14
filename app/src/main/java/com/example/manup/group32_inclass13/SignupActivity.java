package com.example.manup.group32_inclass13;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText fname, lname, email, pwd, rpwd;
    Button btncancel, btnsignup;
    FirebaseAuth auth;
    DatabaseReference mDatabase;
    Toolbar toolbar;

    FirebaseUser user1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        auth= FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
         user1=auth.getCurrentUser();
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.email);
        pwd = (EditText) findViewById(R.id.pwd);
        rpwd = (EditText) findViewById(R.id.rpwd);
        btnsignup = (Button) findViewById(R.id.btnsignup);
        btncancel = (Button) findViewById(R.id.btncancel);
        toolbar=(Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle("MessageMe (Sign Up)!");


        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();



            }
        });

        btncancel.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    /*   private void writeNewUser(String name,String userId) {
           User user = new User(name);
           mDatabase.child("users").child(userId).setValue(user);
       }*/
    private void registerUser() {

        String em= email.getText().toString();
        String p = pwd.getText().toString();

        if (fname.getText().toString().equals("") || !checkName(fname)) {
            Toast.makeText(SignupActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
            fname.setError("please enter a valid name" + "\n" + "Name should only have character input");
        } else if (lname.getText().toString().equals("") || !checkName(lname)) {
            Toast.makeText(SignupActivity.this, "Please enter last name", Toast.LENGTH_SHORT).show();
            lname.setError("please enter a valid name" + "\n" + "Name should only have character input");
        } else if (fname.getText().toString().equals(lname.getText().toString())) {
            Toast.makeText(SignupActivity.this, "Please enter a different value for firstname and lastname", Toast.LENGTH_SHORT).show();
        } else if (email.getText().toString().equals("") || !checkEmail(email)) {
            Toast.makeText(SignupActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
            email.setError("please enter a valid email" + "\n" + "Format- xyz@xyz.com");
        } else if (pwd.getText().toString().equals("")) {
            Toast.makeText(SignupActivity.this, "Please choose password", Toast.LENGTH_SHORT).show();
        } else if (rpwd.getText().toString().equals("")) {
            Toast.makeText(SignupActivity.this, "Please enter the password again to verify", Toast.LENGTH_SHORT).show();
        } else if (!(pwd.getText().toString().equals(rpwd.getText().toString()))) {
            Toast.makeText(SignupActivity.this, "Please enter matching passwords", Toast.LENGTH_SHORT).show();
        } else if (pwd.getText().toString().length() < 6) {
            Toast.makeText(SignupActivity.this, "Password should be of minimum six characters", Toast.LENGTH_SHORT).show();
        } else {
            auth.createUserWithEmailAndPassword(em,p).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        User user=new User();
                        Log.d("usersign", user+"");
                        String key = mDatabase.child("users").push().getKey();
                        String uname = fname.getText().toString()+ " "+lname.getText().toString();
                        user.setUserName(uname);
                        user.setUserID(auth.getCurrentUser().getUid());
                        user.setUserKey(key);
                        Map<String, Object> postValues = user.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put("/users/" + auth.getCurrentUser().getUid(), postValues);
                        mDatabase.updateChildren(childUpdates);
                        Log.d("firstname", user.getUserName());

                        if(user1!=null) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(user.getUserName()).build();
                            user1.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });

                        }
                        Intent in = new Intent(SignupActivity.this, InboxActivity.class);
                        startActivity(in);
                        finish();


                    } else {
                        Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("error",task.getException()+"");
                    }
                }
            });
        }
    }


    public static boolean checkEmail(EditText email) {
        String emailReg = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.getText().toString().matches(emailReg)) {
            return true;
        }
        else
        {
            return false;
        }
    }
    public static boolean checkName(EditText name)
    {
        String nameReg = "[a-zA-Z]+";
        if (name.getText().toString().matches(nameReg))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
