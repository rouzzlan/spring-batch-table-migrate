package com.falcontech.batchpostgresmysql;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({"com.falcontech.batchpostgresmysql.config",
    "com.falcontech.batchpostgresmysql.processor"})
public class BatchPostgresMysqlApplication {

  public static void main(String[] args) {
    SpringApplication.run(BatchPostgresMysqlApplication.class, args);
  }

}
