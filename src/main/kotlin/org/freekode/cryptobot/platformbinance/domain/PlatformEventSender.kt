package org.freekode.cryptobot.platformbinance.domain

import org.freekode.cryptobot.platformbinance.domain.event.PlatformEvent


interface PlatformEventSender {
    fun send(event: PlatformEvent)
}
