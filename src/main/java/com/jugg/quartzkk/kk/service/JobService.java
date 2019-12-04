package com.jugg.quartzkk.kk.service;

import java.util.Date;

/**
 * @Description:
 * @Author: Kevin
 * @Create 2019-12-04 18:13
 */
public interface JobService {
    /**
     * 添加一个定时任务
     * @param jobName
     * @param jobGroup
     * @param time
     */
    void addCronJob(String jobName, String jobGroup, String time);

    /**
     * 添加异步任务
     * @param jobName
     * @param jobGroup
     * @param time
     */
    void addAsyncJob(String jobName, String jobGroup, Date time);

    /**
     * 暂停任务
     * @param jobName
     * @param jobGroup
     */
    void pauseJob(String jobName, String jobGroup);

    /**
     * 恢复任务
     * @param triggerName
     * @param triggerGroup
     */
    void resumeJob(String triggerName, String triggerGroup);

    /**
     * 删除job
     * @param jobName
     * @param jobGroup
     */
    void deleteJob(String jobName, String jobGroup);


}