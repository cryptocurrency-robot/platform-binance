package org.freekode.cryptobot.platformbinance.domain

import java.io.Serializable
import java.math.BigDecimal


data class PlatformValueEvent(
    val platformName: PlatformName,
    val pair: MarketPair,
    val value: BigDecimal,
    val timestamp: Long
) : Serializable
