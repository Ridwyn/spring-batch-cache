package com.example.batchprocessing;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@Slf4j
public class CacheConfig {

  @Autowired
  CacheManager cacheManager;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void insertIntoDB() {

    jdbcTemplate.execute("insert into people(FIRST_NAME,LAST_NAME)values('craig','mitch')");
  }

  @Cacheable("people")
  public List<Person2> fetchPeopleFromDB() {
    return jdbcTemplate.query("select * from people", new BeanPropertyRowMapper<>(Person2.class));
  }

  @CacheEvict("people")
  @Scheduled(cron = "0/60 * * * * *")
  protected void invalidateCache() {
//    cacheManager.getCache("people").invalidate();
    log.info("cache: {} invalidated ",cacheManager.getCache("people").getName());

  }

}
