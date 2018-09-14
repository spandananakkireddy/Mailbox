package com.example.manup.group32_inclass13;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.FormatException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ComposeMsgActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imperson;
    TextView tvsendername;
    EditText etmessage;
    Button btnsendmessage;
    AlertDialog.Builder alertDialog;

    ArrayList<User> userArrayList;
    FirebaseDatabase database;
    AlertDialog dialog;
    ArrayList<String> namelist;
    ArrayList<String> idlist;
    DatabaseReference databaseReference;
    String username;
    String fname, nam;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy, hh:mm a");
    String lname,key,date1;
    Date date = null;
    Message message;
    Message msg1;
    String uname;
    DatabaseReference dref;
    String  rid;
User user1;
User us;
 FirebaseAuth auth;
 FirebaseUser fuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_msg);
        userArrayList = new ArrayList<>();
        namelist= new ArrayList<>();
        idlist= new ArrayList<>();
        us= new User();

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setTitle("Compose Message");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        dref = database.getReference();
        user1 = new User();
        auth = FirebaseAuth.getInstance();
        fuser= auth.getCurrentUser();
        //uname= fuser.getDisplayName();

        imperson = (ImageView) findViewById(R.id.imperson);
        alertDialog = new AlertDialog.Builder(ComposeMsgActivity.this);
        tvsendername = (TextView) findViewById(R.id.tvsendername);
        etmessage = (EditText) findViewById(R.id.etmessage);
        msg1= new Message();
        btnsendmessage = (Button) findViewById(R.id.btnsendmessage);

       if(getIntent() != null && getIntent().getExtras() != null)

        {
            if (getIntent().getExtras().getSerializable(ReadActivity.Mkey) != null) {
                msg1 = (Message) getIntent().getExtras().getSerializable(ReadActivity.Mkey);
                tvsendername.setText(msg1.getUserName());
                 rid= msg1.getUserId();
                 uname=msg1.getToname();
                imperson.setEnabled(false);
                Log.d("msgrid",msg1.toString());
            } else {
                Log.d("error", "");
            }

        }




        DatabaseReference quesy = databaseReference.child("users");
        quesy.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Log.d("data",dataSnapshot+"");
                    User user = dataSnapshot.getValue(User.class);
                    Log.d("user",""+user);
                    userArrayList.add(user);
                    Log.d("userlist",""+userArrayList);
                    namelist.add(user.getUserName());
                    idlist.add(user.getUserID());


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        imperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setCancelable(false);
                alertDialog.setTitle("Users");
                alertDialog.setItems(namelist.toArray(new CharSequence[namelist.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d("list",namelist+"");
                    tvsendername.setText(namelist.get(which));
                    rid = idlist.get(which);
                    //uname= tvsendername.getText().toString();
                    Log.d("id",rid+"");

                    }
                });
                dialog=alertDialog.create();
                alertDialog.show();


            }
        });
        DatabaseReference query=databaseReference.child("users").child(auth.getCurrentUser().getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                us = dataSnapshot.getValue(User.class);

               uname= us.getUserName();


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       btnsendmessage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(etmessage.getText().toString().length() != 0) {
                     if (tvsendername.getText().toString().length() != 0) {

                         senMsg();
                         Toast.makeText(ComposeMsgActivity.this, "Sent", Toast.LENGTH_SHORT).show();
                     } else {
                         Toast.makeText(ComposeMsgActivity.this, "Please select a username", Toast.LENGTH_SHORT).show();
                     }
                 }
                 else
                 {
                     Toast.makeText(ComposeMsgActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                 }

             }
         });

    }

    public void senMsg()
    {
        message = new Message();
        String msg = etmessage.getText().toString();

        key = dref.child("messages").push().getKey();

        Log.d("ruserid",""+rid);
        Log.d("username","inmsg"+uname);
        try {
            date1 = df.format(new Date());
        }catch (Exception p){
            p.printStackTrace();
        }
        message.setDate(date1);
        message.setIsRead(Boolean.FALSE);
        message.setMsg_key(key);
        message.setText(msg);
        message.setToname(tvsendername.getText().toString());
        message.setUserName(uname);
        message.setUserId(auth.getCurrentUser().getUid());
        Map<String,Object> values = message.toMap();
        Map<String,Object> childUpdates = new HashMap<>();
        childUpdates.put(rid+"/Messages/"+key,values);
        dref.updateChildren(childUpdates);
        Intent in = new Intent(ComposeMsgActivity.this,InboxActivity.class);
        startActivity(in);
        finishAffinity();
    }
}
