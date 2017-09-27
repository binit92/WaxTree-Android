package com.waxtree.waxtree.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.waxtree.waxtree.R;
import com.waxtree.waxtree.pojo.Project;
import com.waxtree.waxtree.pojo.ProjectAttribute;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by inbkumar01 on 9/25/2017.
 */

public class ProjectDetailFragment extends Fragment{

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.project_detail_fragment,container,false);
        ButterKnife.bind(this,rootView);

        Bundle b = getArguments();

        if(rootView != null){
            Project project = b.getParcelable("project");
            if(project != null) {

                ProjectAttribute pr = project.getProjectAttribute();
                if(pr != null) {
                    title.setText(getVal(pr.getTitle()));
                    desc.setText(getVal(pr.getDesc()));
                    link.setText(getVal(pr.getLink()));
                    image.setText(getVal(pr.getImage()));
                    startDate.setText(getVal(pr.getStartDate()));
                    endDate.setText(getVal(pr.getEndDate()));
                    email.setText(getVal(pr.getEmail()));
                    type.setText(getVal(pr.getType()));
                }
            }
        }
        return  rootView;
    }

    String getVal(String s){
        if(s ==null && s.isEmpty()){
            return s;
        }else{
            return "unknown";
        }
    }
}
