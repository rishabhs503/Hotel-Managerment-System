FROM openjdk:11
ADD ./target/sms-service-0.0.1-SNAPSHOT.jar /usr/src/sms-service-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "sms-service-0.0.1-SNAPSHOT.jar"]