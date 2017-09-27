package com.waxtree.waxtree.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.waxtree.waxtree.R;
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

        Intent i = getIntent();
        String projectClass = i.getStringExtra("project-class");

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
        for(int i=0; i< MainActivity.allProjects.size();i++){
            Project p = MainActivity.allProjects.get(i);
            if(p.getProjectAttribute().getType().equals(projectClass)){
                listedProject.add(p);
            }
        }
    }
}
