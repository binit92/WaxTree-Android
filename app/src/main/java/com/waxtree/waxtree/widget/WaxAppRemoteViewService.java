package com.waxtree.waxtree.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by inbkumar01 on 9/29/2017.
 */

public class WaxAppRemoteViewService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WaxAppRemoteViewFactory(this.getApplicationContext());
    }

}
