FROM adoptopenjdk/openjdk8:alpine
MAINTAINER Rouslan Khayaouri <rouslan_kh@hotmail.com>
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]