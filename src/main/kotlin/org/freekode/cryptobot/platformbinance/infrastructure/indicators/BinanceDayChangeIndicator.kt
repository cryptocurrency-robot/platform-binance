package org.freekode.cryptobot.platformbinance.infrastructure.indicators

import com.binance.api.client.BinanceApiRestClient
import org.freekode.cryptobot.genericplatformlibrary.domain.GenericMarketPair
import org.freekode.cryptobot.genericplatformlibrary.domain.IndicatorId
import org.freekode.cryptobot.genericplatformlibrary.domain.PlatformIndicator
import org.freekode.cryptobot.genericplatformlibrary.domain.PlatformResponse
import org.freekode.cryptobot.genericplatformlibrary.infrastructure.schedule.SimpleJobScheduler
import org.freekode.cryptobot.platformbinance.domain.MarketPair
import org.freekode.cryptobot.platformbinance.domain.ServerTimeQuery
import org.quartz.TriggerKey
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

@Component
class BinanceDayChangeIndicator(
    private val binanceRestClient: BinanceApiRestClient,
    private val simpleJobScheduler: SimpleJobScheduler,
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

    override fun openStream(pair: GenericMarketPair, callback: (PlatformResponse) -> Unit) {
        val marketPair = MarketPair.valueOf(pair.getName())
        validateStream(marketPair)

        val triggerKey = TriggerKey(INDICATOR_ID_24_PRICE_CHANGE.value + "-trigger")
        val platformCallback = getCallback(marketPair, callback)
        simpleJobScheduler.scheduleJob(triggerKey, 10, platformCallback)

        cache.put(marketPair, triggerKey)
    }

    override fun closeStream(pair: GenericMarketPair) {
        val marketPair = MarketPair.valueOf(pair.getName())
        val triggerKey = cache.get(marketPair)?.get() as TriggerKey
        simpleJobScheduler.unscheduleJob(triggerKey)
    }

    override fun getServerTime(): Long {
        return binanceRestClient.serverTime
    }

    private fun getCallback(marketPair: MarketPair, callback: (PlatformResponse) -> Unit): () -> Unit {
        return {
            val tickerStatistics = binanceRestClient.get24HrPriceStatistics(marketPair.getTitle().uppercase())
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
