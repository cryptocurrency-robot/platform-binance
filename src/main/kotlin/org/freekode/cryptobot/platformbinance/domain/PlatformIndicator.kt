package org.freekode.cryptobot.platformbinance.domain

interface PlatformIndicator {
    fun getIndicatorId(): IndicatorId

    fun openStream(pair: MarketPair, callback: (PlatformResponse) -> Unit)

    fun closeStream(pair: MarketPair)
}
