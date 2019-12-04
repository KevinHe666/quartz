package com.jugg.quartzkk.kk.testjob;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @Description:
 * @Author: Kevin
 * @Create 2019-12-04 16:11
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuartzJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(QuartzJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String taskName = context.getJobDetail().getJobDataMap().getString("name");

        logger.info("---> Quartz job {}, {} <----", new Date(), taskName);
    }

}