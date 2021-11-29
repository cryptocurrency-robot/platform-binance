package org.freekode.cryptobot.platformbinance.infrastructure.indicators

import com.binance.api.client.BinanceApiCallback
import com.binance.api.client.BinanceApiWebSocketClient
import com.binance.api.client.domain.event.AggTradeEvent
import org.freekode.cryptobot.platformbinance.domain.IndicatorId
import org.freekode.cryptobot.platformbinance.domain.MarketPair
import org.freekode.cryptobot.platformbinance.domain.PlatformIndicator
import org.freekode.cryptobot.platformbinance.domain.PlatformResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component
import java.io.Closeable
import java.math.BigDecimal

@Component
class BinancePriceIndicator(
    private val binanceWebSocketClient: BinanceApiWebSocketClient,
    cacheManager: CacheManager
) : PlatformIndicator {

    companion object {
        private val PRICE_INDICATOR_ID = IndicatorId("price")
    }

    private val log: Logger = LoggerFactory.getLogger(BinancePriceIndicator::class.java)

    private val cache: Cache

    init {
        cache = cacheManager.getCache("priceStream")!!
    }

    override fun getIndicatorId(): IndicatorId = PRICE_INDICATOR_ID

    override fun openStream(pair: MarketPair, callback: (PlatformResponse) -> Unit) {
        validateStream(pair)

        val binanceCallback: BinanceApiCallback<AggTradeEvent> = getBinanceCallback(pair, callback)
        val newStream = binanceWebSocketClient.onAggTradeEvent(pair.title.lowercase(), binanceCallback)
        cache.put(pair, newStream)
    }

    override fun closeStream(pair: MarketPair) {
        return (cache.get(pair)?.get() as Closeable).close()
    }

    private fun validateStream(marketPair: MarketPair) {
        val closeable = cache.get(marketPair)
        if (closeable != null) {
            throw IllegalStateException("Such stream is already opened")
        }
    }

    private fun getBinanceCallback(marketPair: MarketPair, callback: (PlatformResponse) -> Unit) = object : BinanceApiCallback<AggTradeEvent> {
        override fun onFailure(cause: Throwable) {
            log.error("Aggregated trade event error", cause)
        }

        override fun onResponse(event: AggTradeEvent) {
            val platformResponse = PlatformResponse(marketPair, PRICE_INDICATOR_ID, BigDecimal(event.price), event.eventTime)
            callback.invoke(platformResponse)
        }
    }
}
