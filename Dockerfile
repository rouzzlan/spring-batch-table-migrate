FROM adoptopenjdk/openjdk11:alpine
MAINTAINER Rouslan Khayaouri <rouslan_kh@hotmail.com>
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]