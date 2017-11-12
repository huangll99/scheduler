package com.hll.scheduler.job;

import org.quartz.*;

/**
 * Created by huangll on 2017/11/12.
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class PrintJob implements Job {

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    System.out.println("ha ha ha...");
  }
}
