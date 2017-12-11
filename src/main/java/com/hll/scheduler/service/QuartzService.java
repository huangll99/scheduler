package com.hll.scheduler.service;

import com.google.common.collect.Lists;
import com.hll.scheduler.config.GroupConst;
import com.hll.scheduler.job.CommonJob;
import com.hll.scheduler.model.SimpleJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        .withIdentity(simpleJob.getJobKey(), GroupConst.SIMPLE_GROUP)
        .withDescription(simpleJob.getDesc())
        .usingJobData("taskClass", simpleJob.getTaskClass())
        .usingJobData("repeatInterval", simpleJob.getRepeatInterval() + "")
        .usingJobData("repeatCount", simpleJob.getRepeatCount() + "")
        .build();

    Trigger trigger;
    if (simpleJob.getRepeatCount() >= 0) {
      trigger = newTrigger()
          .withIdentity(simpleJob.getJobKey(), GroupConst.SIMPLE_GROUP)
          .startNow()
          .withSchedule(simpleSchedule()
              .withIntervalInSeconds(simpleJob.getRepeatInterval())
              .withRepeatCount(simpleJob.getRepeatCount()))
          .build();
    } else {
      trigger = newTrigger()
          .withIdentity(simpleJob.getJobKey(), GroupConst.SIMPLE_GROUP)
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

  public List<SimpleJob> getSimpleTaskList() {

    ArrayList<SimpleJob> tasks = Lists.newArrayList();
    try {
      Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(GroupConst.SIMPLE_GROUP));
      for (JobKey jobKey : jobKeys) {
        SimpleJob simpleJob = new SimpleJob();

        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        simpleJob.setJobKey(jobDetail.getKey().getName());
        simpleJob.setDesc(jobDetail.getDescription());
        simpleJob.setTaskClass(jobDetail.getJobClass().getCanonicalName());


        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        simpleJob.setRepeatCount(Integer.parseInt(jobDataMap.getString("repeatCount")));
        simpleJob.setRepeatInterval(Integer.parseInt(jobDataMap.getString("repeatInterval")));

        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
        triggers.forEach(trigger -> {
          try {
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            simpleJob.setTriggerState(triggerState);
          } catch (SchedulerException e) {
            throw new RuntimeException(e);
          }
        });
        tasks.add(simpleJob);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return tasks;
  }

  public void stopTask(String group, String jobKey) {
    try {
      scheduler.pauseJob(new JobKey(jobKey, group));
    } catch (SchedulerException e) {
      throw new RuntimeException(e);
    }
  }

  public void resumeJob(String group, String jobKey) {
    try {
      scheduler.resumeJob(new JobKey(jobKey, group));
    } catch (SchedulerException e) {
      throw new RuntimeException(e);
    }
  }

  public void removeJob(String group, String jobKey) {
    try {
      scheduler.deleteJob(new JobKey(jobKey, group));
    } catch (SchedulerException e) {
      throw new RuntimeException(e);
    }
  }
}

