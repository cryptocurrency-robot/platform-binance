package org.freekode.cryptobot.platformbinance.domain


interface EventSubmitter {
    fun submitPrice(priceValue: PriceValue)
}
