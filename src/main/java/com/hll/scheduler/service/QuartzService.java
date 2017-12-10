package com.hll.scheduler.service;

import com.hll.scheduler.job.CommonJob;
import com.hll.scheduler.model.SimpleJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by huangll on 2017/12/10.
 */
@Service
public class QuartzService {

  @Autowired
  private Scheduler scheduler;

  public void addSimpleJob(SimpleJob simpleJob) {
    JobDetail job = newJob(CommonJob.class)
        .withIdentity(simpleJob.getJobKey(), simpleJob.getGroup())
        .withDescription(simpleJob.getDesc())
        .usingJobData("taskClass",simpleJob.getTaskClass())
        .build();

    Trigger trigger;
    if (simpleJob.getRepeatCount() >= 0) {
      trigger = newTrigger()
          .withIdentity(simpleJob.getJobKey(), simpleJob.getGroup())
          .startNow()
          .withSchedule(simpleSchedule()
              .withIntervalInSeconds(simpleJob.getRepeatInterval())
              .withRepeatCount(simpleJob.getRepeatCount()))
          .build();
    } else {
      trigger = newTrigger()
          .withIdentity(simpleJob.getJobKey(), simpleJob.getGroup())
          .startNow()
          .withSchedule(simpleSchedule()
              .withIntervalInSeconds(simpleJob.getRepeatInterval())
              .repeatForever())
          .build();
    }

    // Tell quartz to schedule the job using our trigger
    try {
      scheduler.scheduleJob(job, trigger);
    } catch (SchedulerException e) {
      throw new RuntimeException(e);
    }
  }
}
