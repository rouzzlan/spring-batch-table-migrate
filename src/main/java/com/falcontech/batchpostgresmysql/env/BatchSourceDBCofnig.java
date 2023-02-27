package com.falcontech.batchpostgresmysql.env;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "batch.db.source.conf")
public class BatchSourceDBCofnig {
  public final String DRIVER = "org.postgresql.Driver";
  private String dbUrl;
  private String user;
  private String pass;
}
