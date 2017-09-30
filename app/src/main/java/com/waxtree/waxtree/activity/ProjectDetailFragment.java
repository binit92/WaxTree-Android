package com.waxtree.waxtree.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.waxtree.waxtree.R;
import com.waxtree.waxtree.data.schematic.ProjectColumns;
import com.waxtree.waxtree.data.schematic.ProjectProvider;
import com.waxtree.waxtree.pojo.Project;
import com.waxtree.waxtree.pojo.ProjectAttribute;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by inbkumar01 on 9/25/2017.
 */

public class ProjectDetailFragment extends Fragment {

    String prTitle = null;
    String prDesc = null;
    String prLink = null;
    String prImage = null;
    String prStartDate = null;
    String prEndDate = null;
    String prEmail = null;
    String prType = null;

    View rootView;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.desc)
    TextView desc;

    @BindView(R.id.link)
    TextView link;

    @BindView(R.id.image)
    TextView image;

    @BindView(R.id.start_date)
    TextView startDate;

    @BindView(R.id.end_date)
    TextView endDate;

    @BindView(R.id.email)
    TextView email;

    @BindView(R.id.type)
    TextView type;

    @BindView(R.id.favorite)
    MaterialFavoriteButton favorite;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.project_detail_fragment, container, false);
        ButterKnife.bind(this, rootView);

        Bundle b = getArguments();

        if (rootView != null) {
            Project project = b.getParcelable("project");
            if (project != null) {

                ProjectAttribute pr = project.getProjectAttribute();
                if (pr != null) {
                    prTitle = getVal(pr.getTitle());
                    prDesc = getVal(pr.getDesc());
                    prLink = getVal(pr.getLink());
                    prImage = getVal(pr.getImage());
                    prStartDate = getVal(pr.getStartDate());
                    prEndDate = getVal(pr.getEndDate());
                    prEmail = getVal(pr.getEmail());
                    prType = getVal(pr.getType());

                    title.setText(prTitle);
                    desc.setText(prDesc);
                    link.setText(prLink);
                    image.setText(prImage);
                    startDate.setText(prStartDate);
                    endDate.setText(prEndDate);
                    email.setText(prEmail);
                    type.setText(prType);
                }
            }
        }

        favorite.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {
                            // Add everything to database
                            ContentValues projectContent = new ContentValues();
                            projectContent.put(ProjectColumns.TITLE, prTitle);
                            projectContent.put(ProjectColumns.DESC, prDesc);
                            projectContent.put(ProjectColumns.LINK, prLink);
                            projectContent.put(ProjectColumns.IMAGE, prLink);
                            projectContent.put(ProjectColumns.START_DATE, prStartDate);
                            projectContent.put(ProjectColumns.END_DATE, prEndDate);
                            projectContent.put(ProjectColumns.EMAIL, prEmail);
                            projectContent.put(ProjectColumns.TYPE, prType);

                            if (!checkIfProjectExists()) {
                                Uri insertedUri = getContext().getContentResolver().insert(
                                        ProjectProvider.Projects.PROJECTS, projectContent);
                                // The inserted URI contains the id for the row.
                                // Extract the movie ID from the Uri
                                // long insertedid = ContentUris.parseId(insertedUri);
                                buttonView.setAnimateFavorite(true);
                                // Todo : Add Trailers and Reviews too!
                            }
                        } else {
                            getContext().getContentResolver().delete(
                                    ProjectProvider.Projects.PROJECTS,
                                    ProjectColumns.TITLE + "=?", new String[]{prTitle});
                        }
                    }
                });

        return rootView;
    }

    String getVal(String s) {
        if(s!=null && !s.isEmpty()){
            return "unknown";
        }else {
            return s;
        }
    }

    boolean checkIfProjectExists() {
        Cursor checkProject = getContext().getContentResolver()
                .query(ProjectProvider.Projects.PROJECTS
                        , new String[]{ProjectColumns.TITLE}
                        , ProjectColumns.TITLE + "=?"
                        , new String[]{prTitle}
                        , null);
        if (checkProject != null && checkProject.moveToFirst()) {
            return true;
        }
        return false;
    }

}
