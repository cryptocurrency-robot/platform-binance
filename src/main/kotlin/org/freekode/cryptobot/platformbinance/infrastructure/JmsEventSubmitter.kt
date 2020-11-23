package org.freekode.cryptobot.platformbinance.infrastructure

import org.freekode.cryptobot.platformbinance.domain.EventSubmitter
import org.freekode.cryptobot.platformbinance.domain.PriceValue
import org.freekode.cryptobot.platformbinance.domain.PriceValueEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component

@Component
class JmsEventSubmitter(
    @Value("\${event.priceQueueName}") private val priceQueueName: String,
    private val jmsTemplate: JmsTemplate,
) : EventSubmitter {
    override fun submitPrice(priceValue: PriceValue) {
        jmsTemplate.convertAndSend(priceQueueName, PriceValueEvent(priceValue))
    }
}
