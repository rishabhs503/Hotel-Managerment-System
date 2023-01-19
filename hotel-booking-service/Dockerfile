FROM openjdk:11
ADD ./target/hotel-booking-service-0.0.1-SNAPSHOT.jar /usr/src/hotel-booking-service-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "hotel-booking-service-0.0.1-SNAPSHOT.jar"]