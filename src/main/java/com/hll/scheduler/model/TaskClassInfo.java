package com.hll.scheduler.model;

/**
 * Created by huangll on 2017/12/10.
 */
public class TaskClassInfo {

  private String taskName;

  private String taskClass;

  public TaskClassInfo() {
  }

  public TaskClassInfo(String taskName, String taskClass) {
    this.taskName = taskName;
    this.taskClass = taskClass;
  }

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getTaskClass() {
    return taskClass;
  }

  public void setTaskClass(String taskClass) {
    this.taskClass = taskClass;
  }
}
