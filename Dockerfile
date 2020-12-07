FROM openjdk:11

ENV BINANCE_API_KEY=''
ENV BINANCE_API_SECRET=''
ENV BROKER_URL=''
ENV JAR_NAME='platform-binance-0.0.1.jar'

COPY ./target/$JAR_NAME /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT java -jar $JAR_NAME \
	--binance.api.key=$BINANCE_API_KEY \
	--binance.api.secret=$BINANCE_API_SECRET \
	--broker-url=$BROKER_URL
