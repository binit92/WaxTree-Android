package com.waxtree.waxtree.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.waxtree.waxtree.R;
import com.waxtree.waxtree.pojo.Project;
import com.waxtree.waxtree.pojo.ProjectAttribute;
import com.waxtree.waxtree.util.ICompletionCallback;
import com.waxtree.waxtree.util.ProjectClassAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ICompletionCallback {

    private static final String TAG = MainActivity.class.getSimpleName();

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mProjectsRef = null;
    static List<Project> allProjects = new ArrayList<>();

    /*@BindView(R.id.projectClassGrid)*/
    RecyclerView projectClassView;
    ProjectClassAdapter projectClassAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife.bind(this);

        firebaseDatabase.setPersistenceEnabled(true);
        mProjectsRef = firebaseDatabase.getReference().child("projects");

        projectClassView = (RecyclerView) findViewById(R.id.projectClassGrid);
        projectClassView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        projectClassView.setClickable(true);

        try {
            getProjectsFromDB(this);

        }catch (Throwable t){
            t.printStackTrace();
        }

    }


    private void getProjectsFromDB(final ICompletionCallback completionCallback){
        mProjectsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and gain
                // whenever data at this location is updated

                for (DataSnapshot projectClassDataSnapshot : dataSnapshot.getChildren()) {
                    String projectName = projectClassDataSnapshot.getKey();
                    ProjectAttribute projectAttribute = projectClassDataSnapshot.getValue(ProjectAttribute.class);
                    allProjects.add(new Project(projectName,projectAttribute));
                }
                completionCallback.onCompletionCallback(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG,"Failed to read value.", databaseError.toException());

            }

        });
    }



    @Override
    public void onCompletionCallback(boolean isComplete) {
        Log.d(TAG,"Total number of Projects read : " + allProjects.size());
        projectClassAdapter = new ProjectClassAdapter(getApplicationContext(),allProjects,MainActivity.this);
        projectClassView.setAdapter(projectClassAdapter);
    }
}
