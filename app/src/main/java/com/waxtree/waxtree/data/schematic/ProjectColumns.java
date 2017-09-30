package com.waxtree.waxtree.data.schematic;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

/**
 * Created by inbkumar01 on 9/28/2017.
 */

public interface ProjectColumns {

    @DataType(INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";

    @DataType(TEXT)
    String TITLE = "title";

    @DataType(TEXT)
    String DESC = "desc";

    @DataType(TEXT)
    String LINK = "link";

    @DataType(TEXT)
    String IMAGE = "image";

    @DataType(TEXT)
    String START_DATE = "start_date";

    @DataType(TEXT)
    String END_DATE = "end_date";

    @DataType(TEXT)
    String EMAIL = "email";

    @DataType(TEXT)
    String TYPE = "type";
}

