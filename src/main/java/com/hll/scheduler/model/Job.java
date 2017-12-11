package com.hll.scheduler.model;

/**
 * Created by huangll on 2017/11/12.
 */
public class Job {

  private String group;

  private String jobKey;

  private String desc;

  private String taskClass;

  public String getTaskClass() {
    return taskClass;
  }

  public void setTaskClass(String taskClass) {
    this.taskClass = taskClass;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
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
