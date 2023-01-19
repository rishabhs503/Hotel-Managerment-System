FROM openjdk:11
ADD ./target/user-authentication-service-0.0.1-SNAPSHOT.jar /usr/src/user-authentication-service-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "user-authentication-service-0.0.1-SNAPSHOT.jar"]