package com.jugg.quartzkk.kk.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Description:
 * @Author: Kevin
 * @Create 2019-12-04 18:16
 */
public class AsyncJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("========================立即执行的任务，只执行一次===============================");
        /*  这里是写你的业务逻辑代码 */

    }
}
