package org.freekode.cryptobot.platformbinance.infrastructure.indicators

import com.binance.api.client.BinanceApiRestClient
import org.freekode.cryptobot.platformbinance.domain.IndicatorId
import org.freekode.cryptobot.platformbinance.domain.MarketPair
import org.freekode.cryptobot.platformbinance.domain.PlatformIndicator
import org.freekode.cryptobot.platformbinance.domain.PlatformResponse
import org.freekode.cryptobot.platformbinance.domain.ServerTimeQuery
import org.freekode.cryptobot.platformbinance.infrastructure.schedule.JobScheduler
import org.quartz.TriggerKey
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

@Component
class BinanceDayChangeIndicator(
    private val binanceRestClient: BinanceApiRestClient,
    private val jobScheduler: JobScheduler,
    cacheManager: CacheManager
) : PlatformIndicator, ServerTimeQuery {

    companion object {
        private val INDICATOR_ID_24_PRICE_CHANGE = IndicatorId("24h-price-change-percent")
    }

    private val cache: Cache

    init {
        cache = cacheManager.getCache("24hPriceChangeStream")!!
    }

    override fun getIndicatorId(): IndicatorId = INDICATOR_ID_24_PRICE_CHANGE

    override fun openStream(pair: MarketPair, callback: (PlatformResponse) -> Unit) {
        validateStream(pair)

        val triggerKey = TriggerKey(INDICATOR_ID_24_PRICE_CHANGE.value + "-trigger")
        val platformCallback = getCallback(pair, callback)
        jobScheduler.scheduleSimpleCallbackJob(triggerKey, 10, platformCallback)

        cache.put(pair, triggerKey)
    }

    override fun closeStream(pair: MarketPair) {
        val triggerKey = cache.get(pair)?.get() as TriggerKey
        jobScheduler.unscheduleSimpleCallbackJob(triggerKey)
    }

    override fun getServerTime(): Long {
        return binanceRestClient.serverTime
    }

    private fun getCallback(marketPair: MarketPair, callback: (PlatformResponse) -> Unit): () -> Unit {
        return {
            val tickerStatistics = binanceRestClient.get24HrPriceStatistics(marketPair.title.uppercase())
            val priceChangePercent = tickerStatistics.priceChangePercent
            val platformResponse =
                PlatformResponse(marketPair, INDICATOR_ID_24_PRICE_CHANGE, BigDecimal(priceChangePercent), Date().time)
            callback.invoke(platformResponse)
        }
    }

    private fun validateStream(marketPair: MarketPair) {
        val value = cache.get(marketPair)
        if (value != null) {
            throw IllegalStateException("Such stream is already opened")
        }
    }
}
