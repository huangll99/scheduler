package com.hll.scheduler.controller;

import com.hll.scheduler.job.PrintJob;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Created by huangll on 2017/11/12.
 */
@RequestMapping("/test")
@RestController
public class TestController {

  @Autowired
  private Scheduler scheduler;

  @RequestMapping(value = "/demo", method = RequestMethod.GET)
  public String demo() {

    try {
      List<String> jobGroupNames = scheduler.getJobGroupNames();
      jobGroupNames.forEach(group -> {
        System.out.println(group);
        try {
          Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(GroupMatcher.groupEquals(group));
          for (TriggerKey triggerKey : triggerKeys) {
            Trigger trigger = scheduler.getTrigger(triggerKey);
            System.out.println(trigger.getJobKey().getGroup() + " - " + trigger.getJobKey().getName());
            System.out.println(trigger.getNextFireTime());
          }

          Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(group));
          for (JobKey jobKey : jobKeys) {
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            System.out.println(jobDetail.getKey());
            System.out.println(jobDetail.getDescription());
            System.out.println(jobDetail.getJobClass());
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            triggers.forEach(trigger -> {
              System.out.println(trigger.getKey());
              try {
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                System.out.println(triggerState.name());
                System.out.println(triggerState.ordinal());
                JobDataMap jobDataMap = trigger.getJobDataMap();
                jobDataMap.forEach((key, value) -> {
                  System.out.print(key+" : ");
                  System.out.println(value);
                });
              } catch (SchedulerException e) {
                e.printStackTrace();
              }
            });
          }
        } catch (SchedulerException e) {
          e.printStackTrace();
        }
      });
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return "ok";
  }

  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String add() throws SchedulerException {
    JobDetail jobDetail = JobBuilder.newJob(PrintJob.class)
        .withIdentity("print", "demo")
        .requestRecovery(true)
        .build();
    Trigger trigger = TriggerBuilder.newTrigger()
        .forJob(jobDetail)
        .withIdentity("print", "demo")
        //.startAt(new Date(new Date().getTime() + 100000))
        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
        .usingJobData("cron", "forever/5")
        .build();

    scheduler.scheduleJob(jobDetail, trigger);
    return "ok";
  }
}
