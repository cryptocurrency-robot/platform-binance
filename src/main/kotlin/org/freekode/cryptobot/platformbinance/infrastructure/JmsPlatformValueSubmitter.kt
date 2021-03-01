package org.freekode.cryptobot.platformbinance.infrastructure

import org.freekode.cryptobot.platformbinance.domain.PlatformValueSubmitter
import org.freekode.cryptobot.platformbinance.domain.PlatformValueEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.jms.core.JmsTemplate
import org.springframework.stereotype.Component

@Component
class JmsPlatformValueSubmitter(
    @Value("\${event.topic.platformValue}") private val platformValueTopic: String,
    private val jmsTemplate: JmsTemplate,
) : PlatformValueSubmitter {
    override fun submitPrice(platformValueEvent: PlatformValueEvent) {
        jmsTemplate.convertAndSend(platformValueTopic, platformValueEvent)
    }
}
