package org.freekode.cryptobot.platformbinance.infrastructure

import com.binance.api.client.BinanceApiCallback
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiRestClient
import com.binance.api.client.BinanceApiWebSocketClient
import com.binance.api.client.domain.event.AggTradeEvent
import com.binance.api.client.domain.market.TickerStatistics
import org.freekode.cryptobot.platformbinance.domain.MarketPair
import org.freekode.cryptobot.platformbinance.domain.MarketQuery
import org.freekode.cryptobot.platformbinance.domain.PlatformName
import org.freekode.cryptobot.platformbinance.domain.PriceValue
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.Closeable
import java.math.BigDecimal

@Component
class BinanceMarketClient(
    @Value("\${binance.api.key}") private val apiKey: String,
    @Value("\${binance.api.secret}") private val apiSecret: String,
    private val platformName: PlatformName
) : MarketQuery {
    private val logger: Logger = LoggerFactory.getLogger(BinanceMarketClient::class.java)

    private val binanceRestClient: BinanceApiRestClient

    private val binanceWebSocketClient: BinanceApiWebSocketClient

    init {
        val factory = BinanceApiClientFactory.newInstance(apiKey, apiSecret)
        binanceRestClient = factory.newRestClient()
        binanceWebSocketClient = factory.newWebSocketClient()
    }

    override fun priceStream(marketPair: MarketPair, callback: (PriceValue) -> Unit): Closeable {
        val binanceCallback: BinanceApiCallback<AggTradeEvent> = getBinanceCallback(marketPair, callback)
        return binanceWebSocketClient.onAggTradeEvent(marketPair.title.toLowerCase(), binanceCallback)
    }

    override fun getDayPriceStatistic(marketPair: MarketPair): TickerStatistics {
        return binanceRestClient.get24HrPriceStatistics(marketPair.title.toUpperCase())
    }

    private fun getBinanceCallback(marketPair: MarketPair, callback: (PriceValue) -> Unit) = object : BinanceApiCallback<AggTradeEvent> {
        override fun onFailure(cause: Throwable) {
            logger.error("Aggregated trade event error", cause)
        }

        override fun onResponse(event: AggTradeEvent) {
            val priceValue = PriceValue(platformName, marketPair, BigDecimal(event.price), event.eventTime)
            callback.invoke(priceValue)
        }
    }
}
