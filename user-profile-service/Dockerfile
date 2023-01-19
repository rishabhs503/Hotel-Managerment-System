FROM openjdk:11
ADD ./target/user-profile-service-0.0.1-SNAPSHOT.jar /usr/src/user-profile-service-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "user-profile-service-0.0.1-SNAPSHOT.jar"]