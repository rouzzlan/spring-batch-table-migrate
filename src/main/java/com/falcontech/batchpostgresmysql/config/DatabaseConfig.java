package com.falcontech.batchpostgresmysql.config;

import com.falcontech.batchpostgresmysql.env.BatchDBConfig;
import com.falcontech.batchpostgresmysql.env.BatchDestinationDBConfig;
import com.falcontech.batchpostgresmysql.env.BatchSourceDBCofnig;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {
  @Autowired
  private BatchDBConfig batchDBConfig;
  @Autowired
  private BatchSourceDBCofnig batchSourceDBCofnig;
  @Autowired
  private BatchDestinationDBConfig batchDestinationDBConfig;

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource datasource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(batchDBConfig.DRIVER);
    dataSource.setUrl(batchDBConfig.getDbUrl());
    dataSource.setUsername(batchDBConfig.getUser());
    dataSource.setPassword(batchDBConfig.getPass());
    return dataSource;

  }

  @Bean
  public DataSource universitydatasource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(batchDestinationDBConfig.DRIVER);
    dataSource.setUrl(batchDestinationDBConfig.getDbUrl());
    dataSource.setUsername(batchDestinationDBConfig.getUser());
    dataSource.setPassword(batchDestinationDBConfig.getPass());
    return dataSource;
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.postgresdatasource")
  public DataSource postgresdatasource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(batchSourceDBCofnig.DRIVER);
    dataSource.setUrl(batchSourceDBCofnig.getDbUrl());
    dataSource.setUsername(batchSourceDBCofnig.getUser());
    dataSource.setPassword(batchSourceDBCofnig.getPass());
    return dataSource;
  }

  @Bean
  public EntityManagerFactory postgresqlEntityManagerFactory() {
    LocalContainerEntityManagerFactoryBean lem =
        new LocalContainerEntityManagerFactoryBean();

    lem.setDataSource(postgresdatasource());
    lem.setPackagesToScan("com.falcontech.batchpostgresmysql.postgres.entity");
    lem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    lem.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    lem.afterPropertiesSet();

    return lem.getObject();
  }

  @Bean
  public EntityManagerFactory mysqlEntityManagerFactory() {
    LocalContainerEntityManagerFactoryBean lem =
        new LocalContainerEntityManagerFactoryBean();

    lem.setDataSource(universitydatasource());
    lem.setPackagesToScan("com.falcontech.batchpostgresmysql.mysql.entity");
    lem.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    lem.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    lem.afterPropertiesSet();

    return lem.getObject();
  }

  @Bean
  @Primary
  public JpaTransactionManager jpaTransactionManager() {
    JpaTransactionManager jpaTransactionManager = new
        JpaTransactionManager();

    jpaTransactionManager.setDataSource(universitydatasource());
    jpaTransactionManager.setEntityManagerFactory(mysqlEntityManagerFactory());

    return jpaTransactionManager;
  }
}
