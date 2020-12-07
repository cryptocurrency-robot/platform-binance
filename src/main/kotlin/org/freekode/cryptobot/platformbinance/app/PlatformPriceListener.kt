package org.freekode.cryptobot.platformbinance.app

import org.freekode.cryptobot.platformbinance.domain.PlatformPriceEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service


@Service
class PlatformPriceListener {

    private val logger: Logger = LoggerFactory.getLogger(PlatformPriceListener::class.java)

//    @JmsListener(destination = "\${event.platformPriceTopic}")
    fun receiveMessage(eventPlatform: PlatformPriceEvent) {
        logger.info("message = ${eventPlatform.price}")
    }
}
