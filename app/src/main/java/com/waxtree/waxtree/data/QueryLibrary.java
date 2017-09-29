package com.waxtree.waxtree.data;

import android.content.Context;
import android.database.Cursor;

import com.waxtree.waxtree.data.schematic.ProjectProvider;
import com.waxtree.waxtree.pojo.Project;
import com.waxtree.waxtree.pojo.ProjectAttribute;

import java.util.List;

/**
 * Created by inbkumar01 on 9/29/2017.
 */

public class QueryLibrary {

    Context context;
    public QueryLibrary(Context context){
        this.context = context;
    }

    public void getAllData(List<Project> listOfProject){
        try {
            Cursor checkProject = context.getContentResolver()
                    .query(ProjectProvider.Projects.PROJECTS, null, null, null, null);

            while (checkProject.moveToNext()){
                ProjectAttribute pr = new ProjectAttribute(
                        checkProject.getString(checkProject.getColumnIndex("title")),
                        checkProject.getString(checkProject.getColumnIndex("desc")),
                        checkProject.getString(checkProject.getColumnIndex("link")),
                        checkProject.getString(checkProject.getColumnIndex("image")),
                        checkProject.getString(checkProject.getColumnIndex("start_date")),
                        checkProject.getString(checkProject.getColumnIndex("end_date")),
                        checkProject.getString(checkProject.getColumnIndex("email")),
                        checkProject.getString(checkProject.getColumnIndex("type")));

                listOfProject.add(new Project(pr.getTitle(),pr));
            }
            checkProject.close();
        }catch (Throwable t){
            t.printStackTrace();
        }
    }




}
