package org.freekode.cryptobot.platformbinance.app

import org.freekode.cryptobot.platformbinance.domain.IndicatorId
import org.freekode.cryptobot.platformbinance.domain.MarketPair
import org.freekode.cryptobot.platformbinance.domain.PlatformEventSender
import org.freekode.cryptobot.platformbinance.domain.PlatformId
import org.freekode.cryptobot.platformbinance.domain.PlatformIndicatorRegistry
import org.freekode.cryptobot.platformbinance.domain.event.PlatformEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class IndicatorStreamService(
    private val platformId: PlatformId,
    private val platformIndicatorRegistry: PlatformIndicatorRegistry,
    private val platformEventSender: PlatformEventSender
) {
    private val logger: Logger = LoggerFactory.getLogger(IndicatorStreamService::class.java)

    fun getAvailableIndicators(): Set<IndicatorId> {
        return platformIndicatorRegistry.getAvailableIndicators()
    }

    fun subscribeToIndicator(indicatorId: IndicatorId, pair: MarketPair) {
        logger.info("Subscribing for $pair")
        platformIndicatorRegistry.getIndicatorImplementation(indicatorId).openStream(pair) {
            val platformEvent = PlatformEvent(platformId.value, it.pair.name, it.indicatorId.value,
                it.value.toPlainString(), it.timestamp / 1000)
            platformEventSender.send(platformEvent)
        }
    }

    fun unsubscribeFromIndicator(indicatorId: IndicatorId, pair: MarketPair) {
        logger.info("Unsubscribing for $pair")
        platformIndicatorRegistry.getIndicatorImplementation(indicatorId).closeStream(pair)
    }
}
