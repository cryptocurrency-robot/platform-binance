package org.freekode.cryptobot.platformbinance.domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Service


@Service
class Listener {

    private val logger: Logger = LoggerFactory.getLogger(Listener::class.java)

    @JmsListener(destination = "price")
    fun receiveMessage(event: PriceValueEvent) {
        logger.info("message = ${event.priceValue.price}")
    }
}
