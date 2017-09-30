package com.waxtree.waxtree.widget;

import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.waxtree.waxtree.R;
import com.waxtree.waxtree.data.QueryLibrary;
import com.waxtree.waxtree.pojo.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by inbkumar01 on 9/29/2017.
 */

public class WaxAppRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    List<Project> listedProject = new ArrayList<>();
    Context context;

    public WaxAppRemoteViewFactory(Context c) {
        context = c;
        getData();
    }

    public void getData() {
        new QueryLibrary(context).getAllData(listedProject);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        getData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return listedProject.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Log.v(context.getClass().getSimpleName(), "pos: " + i);

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        rv.setTextViewText(R.id.projectName, listedProject.get(i).getProjectName());


        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
