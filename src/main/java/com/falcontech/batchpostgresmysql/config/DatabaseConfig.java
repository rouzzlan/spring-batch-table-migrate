package com.falcontech.batchpostgresmysql.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
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

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSource datasource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3317/spring_batch");
    dataSource.setUsername("user");
    dataSource.setPassword("pass");
    return dataSource;

  }

  @Bean
  public DataSource universitydatasource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3316/university");
    dataSource.setUsername("user");
    dataSource.setPassword("pass");
    return dataSource;
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.postgresdatasource")
  public DataSource postgresdatasource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl("jdbc:postgresql://localhost:5433/university");
    dataSource.setUsername("user");
    dataSource.setPassword("pass");
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
