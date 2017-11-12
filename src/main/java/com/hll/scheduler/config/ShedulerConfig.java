package com.hll.scheduler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by huangll on 2017/11/12.
 */
@Configuration
public class ShedulerConfig {

  @Bean("quartzScheduler")
  public SchedulerFactoryBean schedulerFactoryBean(){
    SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
    schedulerFactoryBean.setAutoStartup(true);
    schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));

    return schedulerFactoryBean;
  }
}
