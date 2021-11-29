package org.freekode.cryptobot.platformbinance.domain

import org.freekode.cryptobot.genericplatformlibrary.domain.GenericMarketPair


enum class MarketPair(private val title: String) : GenericMarketPair {
    BTC_USDT("BTCUSDT"),
    ADA_BTC("ADABTC");

    override fun getName(): String {
        return name
    }

    override fun getTitle(): String {
        return title
    }
}
