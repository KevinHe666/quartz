package com.jugg.quartzkk.kk.testjob;

import org.quartz.*;

/**
 * @Description:quartz的job测试类
 * @Author: Kevin
 * @Create 2019-11-21 10:44
 */
public class HelloJobTest implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        在这里是执行定时任务的逻辑代码
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        //能拿到定时任务里的一些参数等
        String name = jobDataMap.get("name").toString();
        String group = jobDataMap.get("group").toString();
        //这里去执行一些业务代码功能等
        System.out.println("Job name: " + name);
        System.out.println("Job group" + group);
        JobKey key = context.getJobDetail().getKey();

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");

        System.err.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
    }
}
