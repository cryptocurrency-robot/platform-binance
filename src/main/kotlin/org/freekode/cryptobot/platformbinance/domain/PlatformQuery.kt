package org.freekode.cryptobot.platformbinance.domain

import com.binance.api.client.domain.market.TickerStatistics
import java.io.Closeable


interface PlatformQuery {
    fun getServerTime(): Long

    fun findPriceStream(marketPair: MarketPair): Closeable?

    fun openPriceStream(marketPair: MarketPair, callback: (PlatformPriceEvent) -> Unit): Closeable

    fun getDayPriceStatistic(marketPair: MarketPair): TickerStatistics
}

