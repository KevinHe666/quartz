package com.jugg.quartzkk.kk.testjob;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @Description:QuartzTest
 * @Author: Kevin
 * @Create 2019-11-21 10:47
 */
public class QuartzTest {
    public static void main(String[] args) throws SchedulerException, InterruptedException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        JobDetail job = newJob(HelloJobTest.class)
                .withIdentity("job", "group")
                .usingJobData("name", "Kevin")
                .usingJobData("group", "Best")
                .usingJobData("jobSays", "Hello World!")
                .usingJobData("myFloatValue", 3.141f)
                .build();

//        //定义一个触发器触发 在某个时间触发
//        SimpleTrigger trigger1 = (SimpleTrigger) newTrigger()
//                .withIdentity("trigger1", "group1")
//                .startAt(sdf.parse("2019-12-04 09:15:00"))                     // some Date
//                .forJob("job", "group")                 // identify job with name, group strings
//                .build();
//        //定义一个指定时间间隔触发
//        SimpleTrigger trigger2 = newTrigger()
//                .withIdentity("job", "group")
//                //定点触发
////                .startAt(sdf.parse("2018-09-27 10:27:00"))
//                // 五秒钟后触发
//                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.SECOND))
//                .withSchedule(simpleSchedule()
//                        .withIntervalInSeconds(2)
//                        .repeatForever())//无限重复
//                //.withRepeatCount(0))
//                .endAt(DateBuilder.dateOf(22, 55, 0))
//                .build();
//
////        构建CronTriggers每隔一分钟，每天上午8点至下午5点之间
//        Date startDate = new Date();
//        startDate.setTime(startDate.getTime() + 5000);
//
//        Date endDate = new Date();
//        endDate.setTime(startDate.getTime() + 5000);
         CronTrigger cronTrigger = newTrigger()
                .withIdentity("job", "group")
                .withSchedule(cronSchedule("0/2 * * * * ?"))//每两秒执行一次
                .build();
        CronTrigger cronTrigger1 = newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(cronSchedule("0/20 * * * * ?")).build();
        scheduler.scheduleJob(job, cronTrigger);

        TimeUnit.SECONDS.sleep(100);

        scheduler.shutdown();
    }
}
