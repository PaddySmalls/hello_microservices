FROM hypriot/rpi-java
ADD ./target/consumer.jar consumer.jar
COPY api_key /tmp/api_key
EXPOSE 8088:8088
ENTRYPOINT ["java", "-jar", "consumer.jar"]
