package org.freekode.cryptobot.platformbinance

import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiRestClient
import com.binance.api.client.BinanceApiWebSocketClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BinanceConfiguration(
    @Value("\${binance.api.key}") private val apiKey: String,
    @Value("\${binance.api.secret}") private val apiSecret: String,
) {

    private val binanceRestClient: BinanceApiRestClient

    private val binanceWebSocketClient: BinanceApiWebSocketClient

    init {
        val factory = BinanceApiClientFactory.newInstance(apiKey, apiSecret)
        binanceRestClient = factory.newRestClient()
        binanceWebSocketClient = factory.newWebSocketClient()
    }

    @Bean
    fun binanceRestClient(): BinanceApiRestClient = binanceRestClient

    @Bean
    fun binanceWebSocketClient(): BinanceApiWebSocketClient = binanceWebSocketClient
}
