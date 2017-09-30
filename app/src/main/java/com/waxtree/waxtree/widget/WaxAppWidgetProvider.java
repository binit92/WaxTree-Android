package com.waxtree.waxtree.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.waxtree.waxtree.R;
import com.waxtree.waxtree.activity.ProjectActivity;

/**
 * Created by inbkumar01 on 9/29/2017.
 */

public class WaxAppWidgetProvider extends AppWidgetProvider {

    private static final String SHOW_PROJECT_DETAILS_ACTION = "show-project-details-action";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = getProjectListView(context);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context);

        AppWidgetManager appManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, WaxAppWidgetProvider.class);
        RemoteViews updateViews = getProjectListView(context);

        appManager.updateAppWidget(thisWidget, updateViews);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, WaxAppWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widgetList);
        }

        super.onReceive(context, intent);
    }

    private static RemoteViews getProjectListView(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_provider);

        Intent intent = new Intent(context, WaxAppRemoteViewService.class);
        views.setRemoteAdapter(R.id.widgetList, intent);

        Intent ingredientIntent = new Intent(context, WaxAppRemoteViewService.class);
        PendingIntent ingredientPendingIntent = PendingIntent.getActivity(
                context,
                0,
                ingredientIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        views.setPendingIntentTemplate(R.id.widgetList, ingredientPendingIntent);

        // Launch Details Activity ..
        Intent detailsIntent = new Intent(context, ProjectActivity.class);
        detailsIntent.setAction("LAUNCH_ACTIVITY");
        PendingIntent pi = PendingIntent.getActivity(context, 0, detailsIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.projectName, pi);

        return views;
    }
}
