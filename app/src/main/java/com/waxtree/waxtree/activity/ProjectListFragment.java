package com.waxtree.waxtree.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waxtree.waxtree.R;
import com.waxtree.waxtree.pojo.Project;
import com.waxtree.waxtree.util.IProjectSelectCallback;
import com.waxtree.waxtree.util.ProjectAdapter;

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

        rootView = inflater.inflate(R.layout.project_list_fragment, container, false);
        if (rootView != null) {
            projectListView = (RecyclerView) rootView.findViewById(R.id.projectsGrid);
            projectListView.setLayoutManager(new LinearLayoutManager(getContext()));
            projectListView.setClickable(true);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(projectListView.getContext(),
                    DividerItemDecoration.VERTICAL);
            projectListView.addItemDecoration(dividerItemDecoration);

            ProjectAdapter projectAdapter = new ProjectAdapter(getActivity().getApplicationContext(), projectList, this);
            projectListView.setAdapter(projectAdapter);
        }
        return rootView;
    }

    private void fetchFromDb() {
    }

    @Override
    public void onProjectSelect(int projectId) {

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        Bundle b = new Bundle();
        b.putParcelable("project", projectList.get(projectId));

        ProjectDetailFragment pdf = new ProjectDetailFragment();
        pdf.setArguments(b);

        if (isTablet) {
            /* Load the ProjectDetailFragment
            */
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.projectDetailFragment, pdf)
                    .commit();
        } else {
            /* Replace the Current Fragment, with a new Fragment
               and push trasaction onto a backstack (this preserve the backbutton behavior)
               Note:- Creating a new "Activity" really defeats the whole purpose to use fragments anyway ...
            */
            FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                    .beginTransaction();

            // Replace whatever is in the fragment container view with this fragment
            // and add the transaction to a back-stack
            transaction.replace(R.id.projectcontainer, pdf);
            transaction.addToBackStack(null);

            //commit the transaction
            transaction.commit();
        }
    }
}
