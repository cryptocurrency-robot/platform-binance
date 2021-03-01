package org.freekode.cryptobot.platformbinance.infrastructure

import com.binance.api.client.BinanceApiCallback
import com.binance.api.client.BinanceApiClientFactory
import com.binance.api.client.BinanceApiRestClient
import com.binance.api.client.BinanceApiWebSocketClient
import com.binance.api.client.domain.event.AggTradeEvent
import com.binance.api.client.domain.market.TickerStatistics
import org.freekode.cryptobot.platformbinance.domain.*
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

        cache = cacheManager.getCache("priceStreams")!!
    }

    override fun getServerTime(): Long {
        return binanceRestClient.serverTime
    }

    override fun findPriceStream(platformIndicator: PlatformIndicator): Closeable? {
        return cache.get(platformIndicator)?.get() as Closeable
    }

    override fun openPriceStream(platformIndicator: PlatformIndicator, callback: (PlatformValueEvent) -> Unit): Closeable {
        val closeable = cache.get(platformIndicator)
        if (closeable != null) {
            throw IllegalStateException("Such stream is already opened")
        }

        val binanceCallback: BinanceApiCallback<AggTradeEvent> = getBinanceCallback(platformIndicator, callback)
        val newCloseable = binanceWebSocketClient.onAggTradeEvent(platformIndicator.platformSpecificName.toLowerCase(), binanceCallback)
        cache.put(platformIndicator, newCloseable)

        return newCloseable
    }

    override fun getDayPriceStatistic(platformIndicator: PlatformIndicator): TickerStatistics {
        return binanceRestClient.get24HrPriceStatistics(platformIndicator.platformSpecificName.toUpperCase())
    }

    private fun getBinanceCallback(platformIndicator: PlatformIndicator, callback: (PlatformValueEvent) -> Unit) = object : BinanceApiCallback<AggTradeEvent> {
        override fun onFailure(cause: Throwable) {
            log.error("Aggregated trade event error", cause)
        }

        override fun onResponse(event: AggTradeEvent) {
            val priceValue = PlatformValueEvent(platformName, platformIndicator.indicatorName, BigDecimal(event.price), event.eventTime)
            callback.invoke(priceValue)
        }
    }
}
