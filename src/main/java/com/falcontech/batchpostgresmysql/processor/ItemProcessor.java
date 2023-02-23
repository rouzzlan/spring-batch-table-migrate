package com.falcontech.batchpostgresmysql.processor;

import com.falcontech.batchpostgresmysql.postgres.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class ItemProcessor implements org.springframework.batch.item.ItemProcessor<Student, com.falcontech.batchpostgresmysql.mysql.entity.Student> {

  @Override
  public com.falcontech.batchpostgresmysql.mysql.entity.Student process(com.falcontech.batchpostgresmysql.postgres.entity.Student item) throws Exception {

    System.out.println(item.getId());

    com.falcontech.batchpostgresmysql.mysql.entity.Student student = new
        com.falcontech.batchpostgresmysql.mysql.entity.Student();

    student.setId(item.getId());
    student.setFirstName(item.getFirstName());
    student.setLastName(item.getLastName());
    student.setEmail(item.getEmail());
    student.setDeptId(item.getDeptId());
    student.setIsActive(item.getIsActive() != null ?
        Boolean.valueOf(item.getIsActive()) : false);

    return student;

  }

}
