package com.jugg.quartzkk.kk.impl;

import com.jugg.quartzkk.kk.job.AsyncJob;
import com.jugg.quartzkk.kk.job.CronJob;
import com.jugg.quartzkk.kk.service.JobService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author: Kevin
 * @Create 2019-12-04 18:13
 */
@Service
public class JobServiceImpl implements JobService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 创建一个定时任务
     * @param jobName
     * @param jobGroup
     * @param time
     */
    @Override
    public void addCronJob(String jobName, String jobGroup, String time) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                System.out.println("job:" + jobName + " 已存在");
            } else {
                //构建job信息
                jobDetail = JobBuilder.newJob(CronJob.class).withIdentity(jobName, jobGroup).build();
                //用JopDataMap来传递数据
                jobDetail.getJobDataMap().put("taskData", "cron-data");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date =  sdf.parse(time);
                // 把发送时间转换为cron表达式
                SimpleDateFormat cronSdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
                String cronTime = cronSdf.format(date);
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronTime);

                //按新的cronExpression表达式构建一个新的trigger
                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName + "_trigger", jobGroup + "_trigger")
                        .withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAsyncJob(String jobName, String jobGroup, Date time) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            if (jobDetail != null) {
                System.out.println("job:" + jobName + " 已存在");
            }
            else {
                //构建job信息,在用JobBuilder创建JobDetail的时候，有一个storeDurably()方法，可以在没有触发器指向任务的时候，将任务保存在队列中了。然后就能手动触发了
                jobDetail = JobBuilder.newJob(AsyncJob.class).withIdentity(jobName, jobGroup).storeDurably().build();
                jobDetail.getJobDataMap().put("asyncData","this is a async task");


                // 把发送时间转换为cron表达式
                SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
                String dateNowStr = sdf.format(time);
                //表达式调度构建器(即任务执行的时间)
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(dateNowStr);
                //定义name/group
                Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName + "_trigger", jobGroup + "_trigger")
                        //一旦加入scheduler，立即生效
                        .startNow()
                        //使用SimpleTrigger
                        .withSchedule(scheduleBuilder)
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pauseJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName + "_trigger", jobGroup + "_trigger");

            scheduler.pauseTrigger(triggerKey);
            System.out.println("=========================pause job:" + jobName + " success========================");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复任务
     *
     * @param jobName
     * @param jobGroup
     */
    @Override
    public void resumeJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName + "_trigger", jobGroup + "_trigger");
            scheduler.resumeTrigger(triggerKey);
            System.out.println("=========================resume job:" + jobName + " success========================");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteJob(String jobName, String jobGroup) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName,jobGroup);
            scheduler.deleteJob(jobKey);
            System.out.println("=========================delete job:" + jobName + " success========================");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

}
