package org.freekode.cryptobot.platformbinance.domain

import java.io.Serializable
import java.math.BigDecimal


class PriceValue(
    val platformName: PlatformName,
    val pair: MarketPair,
    val price: BigDecimal,
    val eventTime: Long
) : Serializable
