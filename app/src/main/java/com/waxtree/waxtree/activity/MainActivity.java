package com.waxtree.waxtree.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.waxtree.waxtree.R;
import com.waxtree.waxtree.data.FirebaseTask;
import com.waxtree.waxtree.scheduler.WaxJobService;
import com.waxtree.waxtree.util.ICompletionCallback;
import com.waxtree.waxtree.util.ProjectClassAdapter;

import static com.waxtree.waxtree.data.FirebaseTask.allProjects;

public class MainActivity extends AppCompatActivity implements ICompletionCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    FirebaseTask firebaseTask;

  /*  FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mProjectsRef = null;
    static List<Project> allProjects = new ArrayList<>();*/

    /*@BindView(R.id.projectClassGrid)*/
    RecyclerView projectClassView;
    ProjectClassAdapter projectClassAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseTask = FirebaseTask.getInstance(getApplicationContext());
        //ButterKnife.bind(this);

   /*     firebaseDatabase.setPersistenceEnabled(true);
        mProjectsRef = firebaseDatabase.getReference().child("projects");*/


        projectClassView = (RecyclerView) findViewById(R.id.projectClassGrid);
        projectClassView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        projectClassView.setClickable(true);

        try {
            firebaseTask.getProjectsFromDB(this);

        }catch (Throwable t){
            t.printStackTrace();
        }


        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(getApplicationContext()));

        //scheduling automatic sync of projects
        Job dataSync = dispatcher.newJobBuilder()
                .setService(WaxJobService.class)  // The Job Service that will be called
                .setTag(getString(R.string.app_name)) // uniquely identifies the Job
                .setRecurring(false) //on-off Job
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT) //don't persist past a device reboot
                .setTrigger(Trigger.executionWindow(6000,12000)) // starts between 100 to 200 minutes from now
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL) // retry with exponential backoff
                .build();
        dispatcher.mustSchedule(dataSync);

    }


    /*private void getProjectsFromDB(final ICompletionCallback completionCallback){
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
*/




    @Override
    public void onCompletionCallback(boolean isComplete) {
        Log.d(TAG,"Total number of Projects read : " + allProjects.size());
        projectClassAdapter = new ProjectClassAdapter(getApplicationContext(),allProjects,MainActivity.this);
        projectClassView.setAdapter(projectClassAdapter);
    }
}
