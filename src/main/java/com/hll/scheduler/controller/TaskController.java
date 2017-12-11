package com.hll.scheduler.controller;

import com.google.common.collect.Lists;
import com.hll.scheduler.anotation.Task;
import com.hll.scheduler.model.SimpleJob;
import com.hll.scheduler.model.TaskClassInfo;
import com.hll.scheduler.service.QuartzService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangll on 2017/11/12.
 */
@RestController
@RequestMapping("/task")
public class TaskController {

  @Resource
  private WebApplicationContext webApplicationContext;

 @Autowired
 private QuartzService quartzService;

  /**
   * 在Spring的容器中查找加了@Task注解的bean，获取其class
   */
  @RequestMapping(method = RequestMethod.GET)
  public List<TaskClassInfo> getTaskClasses() {
    ArrayList<TaskClassInfo> res = Lists.newArrayList();

    Map<String, Object> taskBeans = webApplicationContext.getBeansWithAnnotation(Task.class);
    for (Map.Entry<String, Object> entry:taskBeans.entrySet()){
      TaskClassInfo taskClassInfo = new TaskClassInfo(entry.getKey(), entry.getValue().getClass().getCanonicalName());
      res.add(taskClassInfo);
    }
    return res;
  }

  /**
   * 添加简单任务
   * repeatInterval:每个多长时间执行一次
   * repeatCount:执行多少次，-1表示永远执行
   */
  @RequestMapping(value = "simple",method = RequestMethod.POST)
  public String addSimpleTask(@RequestBody SimpleJob simpleJob){
    quartzService.addSimpleJob(simpleJob);
    return "ok";
  }

  /**
   * 查询任务列表
   */


  /**
   * 暂停任务
   */

  /**
   * 恢复任务执行
   */


  /**
   * 删除任务
   */


}
