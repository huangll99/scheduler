package com.hll.scheduler.job;

import org.quartz.*;

/**
 * Created by huangll on 2017/11/12.
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class CommonJob implements Job {

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    //从JobExecutionContext中获取要执行的任务class,beanName

    //反射执行任务bean的任务方法

    //记录任务执行日志

  }

}
