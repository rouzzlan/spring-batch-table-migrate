package com.falcontech.batchpostgresmysql.processor;

import com.falcontech.batchpostgresmysql.postgres.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class ItemProcessor implements org.springframework.batch.item.ItemProcessor<Student, com.falcontech.batchpostgresmysql.mysql.entity.Student> {

  @Override
  public com.falcontech.batchpostgresmysql.mysql.entity.Student process(com.falcontech.batchpostgresmysql.postgres.entity.Student item) {

    System.out.println(item.getId());

    com.falcontech.batchpostgresmysql.mysql.entity.Student student = new
        com.falcontech.batchpostgresmysql.mysql.entity.Student();

    student.setId(item.getId());
    student.setFirstName(item.getFirstName());
    student.setLastName(item.getLastName());
    student.setEmail(item.getEmail());

    return student;

  }

}
