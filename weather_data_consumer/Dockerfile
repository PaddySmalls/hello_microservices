FROM hypriot/rpi-java
ADD ./target/consumer.jar consumer.jar
EXPOSE 8088:8088
ENTRYPOINT ["java", "-jar", "consumer.jar"]
