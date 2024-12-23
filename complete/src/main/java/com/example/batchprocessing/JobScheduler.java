package com.example.batchprocessing;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
public class JobScheduler {

  @Autowired
  private JobExplorer jobExplorer;
  @Autowired
  private JobLauncher jobLauncher;
  @Autowired
  private Job importUserJob;

  @Autowired
  private CacheConfig cacheConfig;

  @Scheduled(cron = "0/30 * * * * *")
  private void runJob()
      throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

//    var jp = new JobParameters();
//    jobLauncher.run(importUserJob,jp);
//
//    System.out.println("hello\n");

    cacheConfig.insertIntoDB();
   var list = cacheConfig.fetchPeopleFromDB();

   log.info("total in cache {}",list.size());

  }


}
