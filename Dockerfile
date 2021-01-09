FROM openjdk:11

ENV BINANCE_API_KEY=''
ENV BINANCE_API_SECRET=''
ENV BROKER_URL=''
ENV JAR_NAME='platform-binance-0.0.1.jar'

RUN mkdir /sources
COPY . /sources
WORKDIR /sources
RUN ./mvnw package -DskipTests
RUN mkdir /app
RUN cp ./target/$JAR_NAME /app
RUN rm -rf /sources

WORKDIR /app
EXPOSE 8080
ENTRYPOINT java -jar $JAR_NAME \
	--binance.api.key=$BINANCE_API_KEY \
	--binance.api.secret=$BINANCE_API_SECRET \
	--broker-url=$BROKER_URL
