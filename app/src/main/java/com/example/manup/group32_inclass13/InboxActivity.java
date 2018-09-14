package com.example.manup.group32_inclass13;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.lang.Boolean.TRUE;

public class InboxActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView addmail,logout;
    private FirebaseAuth mAuth;
    InboxAdapter adapter;
    DatabaseReference myRef;
    ListView listView;
    Message message;
    ArrayList<Message> messageArrayList = new ArrayList<>();
    public static String MessageKey="data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        toolbar=(Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
      //  getSupportActionBar().setTitle("Inbox");
        mAuth= FirebaseAuth.getInstance();
        listView = (ListView)findViewById(R.id.listView);
        addmail = (ImageView) findViewById(R.id.addmail);
        logout = (ImageView) findViewById(R.id.logout);
        myRef = FirebaseDatabase.getInstance().getReference();
        adapter = new InboxAdapter(InboxActivity.this,R.layout.row_layout,messageArrayList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        getList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                message = messageArrayList.get(position);
                                                Log.d("message", "" + message);
                                                message.setIsRead(TRUE);
                                                Log.d("messagekey", "" + message.getMsg_key());
                                                myRef.child(mAuth.getCurrentUser().getUid()).child("Messages").child(message.getMsg_key()).child("isRead").setValue(TRUE);
                                                adapter.notifyDataSetChanged();
                                                Intent intent = new Intent(InboxActivity.this, ReadActivity.class);
                                                intent.putExtra(MessageKey, message);
                                                startActivity(intent);

                                            }
                                        });

        adapter.notifyDataSetChanged();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent= new Intent(InboxActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        addmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(InboxActivity.this, ComposeMsgActivity.class);
                startActivity(in);


            }
        });
    }
    public void getList() {
        DatabaseReference selectQuery=myRef.child(mAuth.getCurrentUser().getUid()).child("Messages");
        selectQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("datasnapshot",dataSnapshot+"");
                Message message = dataSnapshot.getValue(Message.class);
                messageArrayList.add(0, message);
                adapter.notifyDataSetChanged();
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
    }
}
