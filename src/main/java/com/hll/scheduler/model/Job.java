package com.hll.scheduler.model;

import org.quartz.Trigger;

/**
 * Created by huangll on 2017/11/12.
 */
public class Job {


  private String jobKey;

  private String desc;

  private String taskClass;

  private Trigger.TriggerState triggerState;

  public Trigger.TriggerState getTriggerState() {
    return triggerState;
  }

  public void setTriggerState(Trigger.TriggerState triggerState) {
    this.triggerState = triggerState;
  }

  public String getTaskClass() {
    return taskClass;
  }

  public void setTaskClass(String taskClass) {
    this.taskClass = taskClass;
  }


  public String getJobKey() {
    return jobKey;
  }

  public void setJobKey(String jobKey) {
    this.jobKey = jobKey;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }
}
