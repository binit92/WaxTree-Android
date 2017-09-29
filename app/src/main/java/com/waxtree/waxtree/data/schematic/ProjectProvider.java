package com.waxtree.waxtree.data.schematic;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by inbkumar01 on 9/28/2017.
 */

@ContentProvider(
        authority = ProjectProvider.AUTHORITY,
        database = ProjectDatabase.class,
        packageName = "com.waxtree.projectprovider")
public final class ProjectProvider{

    public static final String AUTHORITY = "com.waxtree.projectprovider";

    @TableEndpoint(table = ProjectDatabase.PROJECTS)
    public static class Projects{

        @ContentUri(path = "projects",
                type = "vnd.android.cursor.dir/projects",
                defaultSort = ProjectColumns.TITLE + " ASC")
        public static final Uri PROJECTS = Uri.parse("content://"+AUTHORITY+"/projects");
    }
}

