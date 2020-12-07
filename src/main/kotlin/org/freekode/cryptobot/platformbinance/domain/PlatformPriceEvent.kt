package org.freekode.cryptobot.platformbinance.domain

import java.io.Serializable
import java.math.BigDecimal


data class PlatformPriceEvent(
    val platformName: PlatformName,
    val pair: MarketPair,
    val price: BigDecimal,
    val timestamp: Long
) : Serializable
