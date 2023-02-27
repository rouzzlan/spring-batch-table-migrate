package com.falcontech.batchpostgresmysql.env;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "batch.db.conf")
public class BatchDBConfig {
  public final String DRIVER = "com.mysql.cj.jdbc.Driver";
  private String dbUrl;
  private String user;
  private String pass;
}
