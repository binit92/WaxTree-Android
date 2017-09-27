package com.waxtree.waxtree.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waxtree.waxtree.R;
import com.waxtree.waxtree.activity.ProjectActivity;
import com.waxtree.waxtree.pojo.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inbkumar01 on 9/25/2017.
 */

public class ProjectClassAdapter extends RecyclerView.Adapter<ProjectClassAdapter.ViewHolder> {

    Context context;
    List<Project> projects;
    List<String> projectClasses;
    private ICompletionCallback callback;
    private static final String TAG = ProjectClassAdapter.class.getSimpleName();

    public ProjectClassAdapter(Context context, List<Project> projects, ICompletionCallback callback){
        this.context = context;
        this.projects = projects;
        this.projectClasses = getProjectClasses(projects);
        this.callback = callback;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context c  = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(c);

        //Inflate the custom layout
        View projectClassName = inflater.inflate(R.layout.project_class_entry,parent,false);

        // Return a new holder instance
        ViewHolder viewHolder = new ProjectClassAdapter.ViewHolder(projectClassName);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProjectClassAdapter.ViewHolder holder,  int position) {
        // Get the data model based on position
        final String projectClass = projectClasses.get(position);

        TextView projectClassView = holder.projectClassTextView;
        projectClassView.setText(projectClass);
        projectClassView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProjectActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("project-class",projectClass);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return projectClasses.size();
    }


    List<String> getProjectClasses(List<Project> allProjects){
        List<String> projectClasses = new ArrayList<>();
        for(int i=0; i<allProjects.size();i++){
            Project p = allProjects.get(i);
            String projectClass = p.getProjectAttribute().getType();

            //Iterate to see projectClass exists in list
            boolean found = false;
            for(int j=0;j<projectClasses.size();j++){
                if(projectClasses.get(j).equals(projectClass)){
                    found = true;
                    break;
                }
            }
            if(!found) {
                projectClasses.add(projectClass);
            }
        }
        return projectClasses;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        TextView projectClassTextView;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            projectClassTextView = (TextView) itemView.findViewById(R.id.textProjectClass);
        }
    }

}
