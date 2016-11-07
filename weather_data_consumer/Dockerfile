FROM openjdk:8-jre
ADD ./target/consumer.jar consumer.jar
EXPOSE 8088:8088
ENTRYPOINT ["java", "-jar", "consumer.jar"]
