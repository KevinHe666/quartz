package com.jugg.quartzkk.kk.controller;

import com.jugg.quartzkk.kk.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Description:
 * @Author: Kevin
 * @Create 2019-12-04 18:17
 */

@RestController
@RequestMapping("/quartztest")
public class JobController {
    @Autowired
    private JobService jobService;


    /**
     * 创建cron任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @RequestMapping(value = "/cron", method = RequestMethod.POST)
    public String startCronJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup, @RequestParam("time") String time) {
        jobService.addCronJob(jobName, jobGroup, time);
        return "create cron task success";
    }

    /**
     * 创建异步任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @RequestMapping(value = "/async", method = RequestMethod.POST)
    public String startAsyncJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup, @RequestParam("time") String time) {
        //这里时间注意修改
        jobService.addAsyncJob(jobName, jobGroup, new Date());
        return "create async task success";
    }

    /**
     * 暂停任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @RequestMapping(value = "/pause", method = RequestMethod.POST)
    public String pauseJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
        jobService.pauseJob(jobName, jobGroup);
        return "pause job success";
    }

    /**
     * 恢复任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @RequestMapping(value = "/resume", method = RequestMethod.POST)
    public String resumeJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
        jobService.resumeJob(jobName, jobGroup);
        return "resume job success";
    }

    /**
     * 删除务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    public String deleteJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
        jobService.deleteJob(jobName, jobGroup);
        return "delete job success";
    }

}
