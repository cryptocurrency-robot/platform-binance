FROM openjdk:11
COPY ./target/binance-0.0.1.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "platform-binance-0.0.1.jar"]
