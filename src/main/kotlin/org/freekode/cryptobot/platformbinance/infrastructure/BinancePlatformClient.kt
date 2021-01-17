package org.freekode.cryptobot.platformbinance.infrastructure

import com.binance.api.client.BinanceApiCallback
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiRestClient
import com.binance.api.client.BinanceApiWebSocketClient
import com.binance.api.client.domain.event.AggTradeEvent
import com.binance.api.client.domain.market.TickerStatistics
import org.freekode.cryptobot.platformbinance.domain.MarketPair
import org.freekode.cryptobot.platformbinance.domain.PlatformName
import org.freekode.cryptobot.platformbinance.domain.PlatformPriceEvent
import org.freekode.cryptobot.platformbinance.domain.PlatformQuery
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component
import java.io.Closeable
import java.math.BigDecimal

@Component
class BinancePlatformClient(
    @Value("\${binance.api.key}") private val apiKey: String,
    @Value("\${binance.api.secret}") private val apiSecret: String,
    private val platformName: PlatformName,
    cacheManager: CacheManager
) : PlatformQuery {

    private val log: Logger = LoggerFactory.getLogger(BinancePlatformClient::class.java)

    private val binanceRestClient: BinanceApiRestClient

    private val binanceWebSocketClient: BinanceApiWebSocketClient

    private val cache: Cache

    init {
        val factory = BinanceApiClientFactory.newInstance(apiKey, apiSecret)
        binanceRestClient = factory.newRestClient()
        binanceWebSocketClient = factory.newWebSocketClient()

        cache = cacheManager.getCache("priceStream")!!
    }

    override fun getServerTime(): Long {
        return binanceRestClient.serverTime
    }

    override fun findPriceStream(marketPair: MarketPair): Closeable? {
        return cache.get(marketPair)?.get() as Closeable
    }

    override fun openPriceStream(marketPair: MarketPair, callback: (PlatformPriceEvent) -> Unit): Closeable {
        val closeable = cache.get(marketPair)
        if (closeable != null) {
            throw IllegalStateException("Such stream is already opened")
        }

        val binanceCallback: BinanceApiCallback<AggTradeEvent> = getBinanceCallback(marketPair, callback)
        val newCloseable = binanceWebSocketClient.onAggTradeEvent(marketPair.title.toLowerCase(), binanceCallback)
        cache.put(marketPair, newCloseable)

        return newCloseable
    }

    override fun getDayPriceStatistic(marketPair: MarketPair): TickerStatistics {
        return binanceRestClient.get24HrPriceStatistics(marketPair.title.toUpperCase())
    }

    private fun getBinanceCallback(marketPair: MarketPair, callback: (PlatformPriceEvent) -> Unit) = object : BinanceApiCallback<AggTradeEvent> {
        override fun onFailure(cause: Throwable) {
            log.error("Aggregated trade event error", cause)
        }

        override fun onResponse(event: AggTradeEvent) {
            val priceValue = PlatformPriceEvent(platformName, marketPair, BigDecimal(event.price), event.eventTime)
            callback.invoke(priceValue)
        }
    }
}
