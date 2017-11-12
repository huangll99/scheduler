package com.hll.scheduler.controller;

import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * Created by huangll on 2017/11/12.
 */
public class BaseController {

  @Resource
  protected WebApplicationContext webApplicationContext;

  protected Scheduler scheduler;

  @Resource
  public void setScheduler(Scheduler scheduler) {
    this.scheduler = scheduler;
    try {
      SchedulerContext schedulerContext = scheduler.getContext();
      schedulerContext.put("WebApplicationContext", webApplicationContext);
    } catch (SchedulerException e) {
      throw new RuntimeException(e);
    }
  }
}
