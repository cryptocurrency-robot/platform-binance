package org.freekode.cryptobot.platformbinance.infrastructure

import org.freekode.cryptobot.platformbinance.domain.PlatformPriceSubmitter
import org.freekode.cryptobot.platformbinance.domain.PlatformPriceEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component

@Component
class JmsPlatformPriceSubmitter(
    @Value("\${event.platformPriceTopic}") private val platformPriceTopic: String,
    private val jmsTemplate: JmsTemplate,
) : PlatformPriceSubmitter {
    override fun submitPrice(platformPriceEvent: PlatformPriceEvent) {
        jmsTemplate.convertAndSend(platformPriceTopic, platformPriceEvent)
    }
}
