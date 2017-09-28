package com.waxtree.waxtree.scheduler;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.waxtree.waxtree.data.FirebaseTask;

/**
 * Created by inbkumar01 on 9/27/2017.
 */

public class WaxJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        FirebaseTask fr = FirebaseTask.getInstance(getApplicationContext());
        fr.tryAutomaticSignIn();
        fr.getProjectsFromDB(null);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
