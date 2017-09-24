package com.waxtree.waxtree.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.waxtree.waxtree.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*// Write a message to the database
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference("message");

    myRef.setValue("Hello World!");
    */
}
