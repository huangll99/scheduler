package com.hll.scheduler.model;

/**
 * Created by huangll on 2017/12/10.
 */
public class SimpleJob extends Job {

  private int repeatInterval;

  private int repeatCount;

  public int getRepeatInterval() {
    return repeatInterval;
  }

  public void setRepeatInterval(int repeatInterval) {
    this.repeatInterval = repeatInterval;
  }

  public int getRepeatCount() {
    return repeatCount;
  }

  public void setRepeatCount(int repeatCount) {
    this.repeatCount = repeatCount;
  }
}
