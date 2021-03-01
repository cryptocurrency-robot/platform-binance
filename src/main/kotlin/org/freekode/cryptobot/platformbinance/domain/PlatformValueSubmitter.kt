package org.freekode.cryptobot.platformbinance.domain


interface PlatformValueSubmitter {
    fun submitPrice(platformValueEvent: PlatformValueEvent)
}
