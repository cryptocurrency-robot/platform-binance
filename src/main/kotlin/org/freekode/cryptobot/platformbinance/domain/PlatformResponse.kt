package org.freekode.cryptobot.platformbinance.domain

import java.math.BigDecimal

data class PlatformResponse(
    val pair: MarketPair,
    val indicatorId: IndicatorId,
    val value: BigDecimal,
    val timestamp: Long
)
