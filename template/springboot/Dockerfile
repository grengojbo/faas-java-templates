FROM maven:3.5-jdk-8-alpine as builder

ENV APP_HOME=/root/springboot/

RUN mkdir -p $APP_HOME/function
RUN apk add xmlstarlet

WORKDIR $APP_HOME

COPY . .
RUN xmlstarlet ed -L  -N w=http://maven.apache.org/POM/4.0.0         --update "//w:project/w:artifactId"            --value handler  function/pom.xml
RUN xmlstarlet ed -L  -N w=http://maven.apache.org/POM/4.0.0         --update "//w:project/w:groupId"            --value function function/pom.xml
RUN xmlstarlet ed -L  -N w=http://maven.apache.org/POM/4.0.0         --update "//w:project/w:version"            --value 1.0-SNAPSHOT  function/pom.xml
RUN cd function \
    && mvn clean install \
    && cd ../ \
    && mvn clean install -DskipTests

FROM openfaas/of-watchdog:0.7.6 as watchdog

FROM openjdk:8u212-jdk-alpine as ship

COPY --from=watchdog /fwatchdog /usr/bin/fwatchdog
RUN chmod +x /usr/bin/fwatchdog                   

#FROM openjdk:8-alpine

RUN apk --no-cache add ca-certificates

COPY --from=builder /root/springboot/target/*.jar app.jar
COPY --from=watchdog /fwatchdog .

ENV fprocess="java -jar app.jar"
ENV mode="http"
ENV upstream_url="http://127.0.0.1:8082"
EXPOSE 8080

CMD ["./fwatchdog"]

