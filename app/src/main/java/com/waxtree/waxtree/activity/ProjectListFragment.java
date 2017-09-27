package com.waxtree.waxtree.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waxtree.waxtree.R;
import com.waxtree.waxtree.pojo.Project;
import com.waxtree.waxtree.util.IProjectSelectCallback;

import java.util.List;

/**
 * Created by inbkumar01 on 9/25/2017.
 */

public class ProjectListFragment extends Fragment implements IProjectSelectCallback {

    private static final String LOG_TAG = ProjectListFragment.class.getSimpleName();
    View rootView;
    RecyclerView projectListView;
    List<Project> projectList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        projectList = bundle.getParcelableArrayList("project-list");

        rootView = inflater.inflate(R.layout.project_list_fragment,container,false);
        if(rootView != null){

        }
        return rootView;
    }

    private void fetchFromDb(){}

    @Override
    public void onProjectSelect(int projectId) {

    }
}
