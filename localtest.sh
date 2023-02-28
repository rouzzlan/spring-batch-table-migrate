#!/bin/bash

export BATCH_ENABLED=true
export BATCH_DB_URL=jdbc:mysql://localhost:3319/spring_batch
export BATCH_DB_USER=user
export BATCH_DB_PASS=pass
export BATCH_SOURCE_DB_URL=jdbc:postgresql://192.168.100.166:54033/university
export BATCH_DEST_DB_URL=jdbc:mysql://192.168.100.166:33016/university

java -jar ./target/batch-postgres-mysql-0.0.1-SNAPSHOT.jar
