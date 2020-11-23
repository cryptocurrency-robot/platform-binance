package org.freekode.cryptobot.platformbinance.domain

import com.binance.api.client.domain.market.TickerStatistics
import java.io.Closeable


interface MarketQuery {
    fun priceStream(marketPair: MarketPair, callback: (PriceValue) -> Unit): Closeable

    fun getDayPriceStatistic(marketPair: MarketPair): TickerStatistics
}

