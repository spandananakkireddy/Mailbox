package com.example.manup.group32_inclass13;

//Priyanka Manusanipally - 801017222
//Sai Spandana Nakkireddy - 801023658

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ReadActivity extends AppCompatActivity {
    Message message;
    TextView tvfrommm, tvmsg;
    ImageView imdelete,imsend;
    FirebaseDatabase database;
    DatabaseReference dref;
    FirebaseAuth auth;
    String key;
    Message m;
    String  d;
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy, hh:mm a");
    public static final String Mkey= "msg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        tvfrommm = (TextView) findViewById(R.id.tvfrommm);
        tvmsg= (TextView) findViewById(R.id.tvmsg);
        database= FirebaseDatabase.getInstance();
        dref= database.getReference();
        auth = FirebaseAuth.getInstance();
        imdelete= (ImageView) findViewById(R.id.imdelete);
        imsend= (ImageView) findViewById(R.id.imsend);
        m= new Message();


        if(getIntent().getExtras().getSerializable(InboxActivity.MessageKey) != null)
        {
            message= (Message) getIntent().getExtras().getSerializable(InboxActivity.MessageKey);
            tvfrommm.setText(message.getUserName());
            tvmsg.setText(message.getText());

        }

        imsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(ReadActivity.this, ComposeMsgActivity.class);

              intent.putExtra(Mkey, message);
              startActivity(intent);
            }
        });
        imdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dref.child(auth.getCurrentUser().getUid()).child("Messages").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        key = message.getMsg_key();
                          dref.child(auth.getCurrentUser().getUid()).child("Messages").child(key).removeValue();

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
                Intent intent = new Intent(ReadActivity.this,InboxActivity.class);
                startActivity(intent);
                finishAffinity();

            }
        });

    }
}
