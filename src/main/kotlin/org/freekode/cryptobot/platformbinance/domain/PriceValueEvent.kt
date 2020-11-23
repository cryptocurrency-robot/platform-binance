package org.freekode.cryptobot.platformbinance.domain

import java.io.Serializable


data class PriceValueEvent(
    val priceValue: PriceValue
) : Serializable
