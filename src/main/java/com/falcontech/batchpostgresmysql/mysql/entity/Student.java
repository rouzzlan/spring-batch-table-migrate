package com.falcontech.batchpostgresmysql.mysql.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "student")
public class Student {
  @Id
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  private String email;

  @Column(name = "dept_id")
  private Long deptId;

  @Column(name = "is_active")
  private Boolean isActive;
}
