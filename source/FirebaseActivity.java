package com.ponduri.aselab3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseActivity extends AppCompatActivity {

  private Button Logout;

  private TextView MsgTxt;

  private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
  private DatabaseReference mRootReference = firebaseDatabase.getReference();
  private DatabaseReference mchildReference = mRootReference.child("message");



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_firebase);

    MsgTxt = (TextView)findViewById(R.id.firebase_text);
    MsgTxt.setText("Message appear Here...");

    Logout = (Button) findViewById(R.id.lpLogout);

    Logout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(FirebaseActivity.this, MainActivity.class));

      }
    });

  }

  @Override
  protected void onStart() {
    super.onStart();

    mchildReference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {

        String message = dataSnapshot.getValue(String.class);
        MsgTxt.setText(message);

      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }
}
