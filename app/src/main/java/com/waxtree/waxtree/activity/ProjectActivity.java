package com.waxtree.waxtree.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.waxtree.waxtree.R;
import com.waxtree.waxtree.data.FirebaseTask;
import com.waxtree.waxtree.data.QueryLibrary;
import com.waxtree.waxtree.pojo.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inbkumar01 on 9/25/2017.
 */

public class ProjectActivity extends AppCompatActivity {

    List<Project> listedProject = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity);

        String projectClass;
        Intent i = getIntent();
        int pos = i.getIntExtra("position",-1);
        if(pos == 0){
            projectClass = getString(R.string.Favorites);

        }else {
            projectClass = i.getStringExtra("project-class");
        }

        if(projectClass!= null && !projectClass.isEmpty()){
            getProjectsOfSpecifiedClass(projectClass);
        }

        if(savedInstanceState == null){
            boolean isTablet = getResources().getBoolean(R.bool.isTablet);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("project-list",(ArrayList<? extends Parcelable>) listedProject);

            ProjectListFragment plf = new ProjectListFragment();
            plf.setArguments(bundle);

            if(isTablet){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.projectListFragment,plf)
                        .commit();

            }else{
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.projectcontainer,plf)
                        .commit();
            }
        }
    }

    // List all the projects from a projectclass
    void getProjectsOfSpecifiedClass(String projectClass){

        if(projectClass!= null && !projectClass.isEmpty()){
            if(projectClass.equals(getString(R.string.Favorites))){
                //Get from DB
              new QueryLibrary(getApplicationContext()).getAllData(listedProject);

            }else{
                for(int i = 0; i< FirebaseTask.allProjects.size(); i++){
                    Project p = FirebaseTask.allProjects.get(i);
                    if(p.getProjectAttribute().getType().equals(projectClass)){
                        listedProject.add(p);
                    }
                }
            }
        }
    }


}
