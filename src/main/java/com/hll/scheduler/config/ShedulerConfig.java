package com.hll.scheduler.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * Created by huangll on 2017/11/12.
 */
@Configuration
public class ShedulerConfig {

  @Resource
  protected WebApplicationContext webApplicationContext;


  @Bean("quartzScheduler")
  public SchedulerFactoryBean schedulerFactoryBean(){
    SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
    schedulerFactoryBean.setAutoStartup(true);
    schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));
    return schedulerFactoryBean;
  }

  @Bean
  public SchedulerConfigBean schedulerConfigBean(Scheduler scheduler){
    try {
      SchedulerContext schedulerContext = scheduler.getContext();
      schedulerContext.put("WebApplicationContext", webApplicationContext);
    } catch (SchedulerException e) {
      throw new RuntimeException(e);
    }
    return new SchedulerConfigBean();
  }

  class SchedulerConfigBean{

  }
}
