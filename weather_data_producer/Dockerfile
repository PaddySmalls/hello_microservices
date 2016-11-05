FROM hypriot/rpi-java
ADD ./target/producer.jar producer.jar
COPY /tmp/api_key /tmp/api_key
ENTRYPOINT ["java", "-jar", "producer.jar"]
