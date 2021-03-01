package org.freekode.cryptobot.platformbinance.domain


interface PlatformPriceSubmitter {
    fun submitPrice(platformValueEvent: PlatformValueEvent)
}
