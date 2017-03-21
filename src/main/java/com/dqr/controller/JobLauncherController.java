package com.dqr.controller;

import lombok.extern.java.Log;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Job Launch Controller.
 *
 * Note(s):
 * This interface makes absolutely no guarantees about whether or not calls to it are executed synchronously or
 * asynchronously. The javadocs for specific implementations should be checked to ensure callers fully understand how
 * the job will be run.
 *
 * Ex.
 * wget "http://localhost:8080/joblauncher?job=<somejob>&param1=value1&param2=value2"
 *
 * wget "http://localhost:8080/launchJob?job=processOrderJob"
 *
 * Created by dqromney on 3/17/17.
 */
@RestController
@Log
public class JobLauncherController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @RequestMapping("/launchJob")
    public String handle() throws Exception {
        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch(Exception e) {
            log.info(e.getMessage());
        }
        return "Done";
    }
}
