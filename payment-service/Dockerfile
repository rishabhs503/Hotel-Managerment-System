FROM openjdk:11
ADD ./target/payment-service-0.0.1-SNAPSHOT.jar /usr/src/payment-service-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "payment-service-0.0.1-SNAPSHOT.jar"]