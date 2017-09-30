package com.waxtree.waxtree.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waxtree.waxtree.R;
import com.waxtree.waxtree.pojo.Project;

import java.util.List;

/**
 * Created by inbkumar01 on 9/27/2017.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    Context context;
    List<Project> listOfProjects;
    private IProjectSelectCallback callback;

    public ProjectAdapter(Context context, List<Project> listOfProjects, IProjectSelectCallback callback) {
        this.context = context;
        this.listOfProjects = listOfProjects;
        this.callback = callback;
    }

    @Override
    public ProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View projectNameView = inflater.inflate(R.layout.projects_entry, parent, false);

        // Return a new viewholder instance
        ProjectAdapter.ViewHolder viewHolder = new ProjectAdapter.ViewHolder(projectNameView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProjectAdapter.ViewHolder holder, final int position) {
        final Project project = listOfProjects.get(position);

        TextView textView = holder.projectName;
        textView.setText(project.getProjectName());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onProjectSelect(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfProjects.size();
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView projectName;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            projectName = (TextView) itemView.findViewById(R.id.textProjectName);
        }
    }
}
