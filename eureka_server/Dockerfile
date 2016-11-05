FROM openjdk:8-jre
EXPOSE 8761:8761
ADD ./target/eureka-server.jar eureka-server.jar
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
