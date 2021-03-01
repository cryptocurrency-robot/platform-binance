package org.freekode.cryptobot.platformbinance.domain

import com.binance.api.client.domain.market.TickerStatistics
import java.io.Closeable


interface PlatformQuery {
    fun getServerTime(): Long

    fun findPriceStream(platformIndicator: PlatformIndicator): Closeable?

    fun openPriceStream(platformIndicator: PlatformIndicator, callback: (PlatformValueEvent) -> Unit): Closeable

    fun getDayPriceStatistic(platformIndicator: PlatformIndicator): TickerStatistics
}

