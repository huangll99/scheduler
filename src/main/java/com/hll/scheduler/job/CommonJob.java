package com.hll.scheduler.job;

import org.quartz.*;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;

/**
 * Created by huangll on 2017/11/12.
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CommonJob implements Job {

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    //从JobExecutionContext中获取要执行的任务class
    String taskClass = context.getJobDetail().getJobDataMap().getString("taskClass");
    try {
      WebApplicationContext webApplicationContext = (WebApplicationContext) context.getScheduler().getContext().get("WebApplicationContext");
      Object bean = webApplicationContext.getBean(Class.forName(taskClass));
      Method method = bean.getClass().getMethod("execute");
      method.invoke(bean);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    //反射执行任务bean的任务方法

    //记录任务执行日志

  }

}
