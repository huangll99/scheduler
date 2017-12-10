package com.hll.scheduler.task;

import com.hll.scheduler.anotation.Task;

/**
 * Created by huangll on 2017/11/12.
 */
@Task("demo任务")
public class DemoTask implements ITask {

  @Override
  public void execute() {
    System.out.println("run task....");
  }
}
