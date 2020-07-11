FROM maven:3.5-jdk-8-alpine as builder

ENV APP_HOME=/root/springboot/

RUN mkdir -p $APP_HOME/function

WORKDIR $APP_HOME

COPY . .

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
ENV upstream_url="http://127.0.0.1:8081"

CMD ["./fwatchdog"]

