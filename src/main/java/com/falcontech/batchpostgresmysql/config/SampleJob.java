package com.falcontech.batchpostgresmysql.config;

import com.falcontech.batchpostgresmysql.processor.ItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;

@Configuration
public class SampleJob {

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  private ItemProcessor firstItemProcessor;

  @Autowired
  @Qualifier("postgresqlEntityManagerFactory")
  private EntityManagerFactory postgresqlEntityManagerFactory;

  @Autowired
  @Qualifier("mysqlEntityManagerFactory")
  private EntityManagerFactory mysqlEntityManagerFactory;

  @Autowired
  private JpaTransactionManager jpaTransactionManager;


  @Bean
  public Job chunkJob() {
    return jobBuilderFactory.get("Chunk Job")
        .incrementer(new RunIdIncrementer())
        .start(firstChunkStep())
        .build();
  }

  private Step firstChunkStep() {
    return stepBuilderFactory.get("First Chunk Step")
        .<com.falcontech.batchpostgresmysql.postgres.entity.Student, com.falcontech.batchpostgresmysql.mysql.entity.Student>chunk(10)
        .reader(jpaCursorItemReader())
        .processor(firstItemProcessor)
        .writer(jpaItemWriter())
        .faultTolerant()
        .skip(Throwable.class)
        .skipLimit(100)
        .retryLimit(3)
        .retry(Throwable.class)
        .transactionManager(jpaTransactionManager)
        .build();
  }

  public JpaCursorItemReader<com.falcontech.batchpostgresmysql.postgres.entity.Student> jpaCursorItemReader() {
    JpaCursorItemReader<com.falcontech.batchpostgresmysql.postgres.entity.Student> jpaCursorItemReader =
        new JpaCursorItemReader<>();

    jpaCursorItemReader.setEntityManagerFactory(postgresqlEntityManagerFactory);

    jpaCursorItemReader.setQueryString("From Student");

    return jpaCursorItemReader;
  }

  public JpaItemWriter<com.falcontech.batchpostgresmysql.mysql.entity.Student> jpaItemWriter() {
    JpaItemWriter<com.falcontech.batchpostgresmysql.mysql.entity.Student> jpaItemWriter =
        new JpaItemWriter<>();

    jpaItemWriter.setEntityManagerFactory(mysqlEntityManagerFactory);

    return jpaItemWriter;
  }
}
