package com.waxtree.waxtree.data.schematic;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by inbkumar01 on 9/28/2017.
 */
@Database(version = ProjectDatabase.VERSION,
        packageName = "com.waxtree.projectprovider")
public final class ProjectDatabase {

    public static final int VERSION = 1;

    @Table(ProjectColumns.class)
    public static final String PROJECTS= "projects";

}

