FROM openjdk:11
ADD ./target/hotel-property-service-0.0.1-SNAPSHOT.jar /usr/src/hotel-property-service-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "hotel-property-service-0.0.1-SNAPSHOT.jar"]