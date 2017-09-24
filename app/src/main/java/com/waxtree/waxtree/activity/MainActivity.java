package com.waxtree.waxtree.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waxtree.waxtree.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference();
    DatabaseReference mProjectsRef = myRef.child("projects");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            getProjectsFromDB();
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    private void getProjectsFromDB(){
        firebaseDatabase.setPersistenceEnabled(true);
        mProjectsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and gain
                // whenever data at this location is updated

                String value =  dataSnapshot.getValue(String.class);
                Log.d(TAG,"Value is: "+ value);
                //arrayList.add(val);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG,"Failed to read value.", databaseError.toException());

            }
        });
    }


}
