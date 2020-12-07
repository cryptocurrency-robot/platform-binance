package org.freekode.cryptobot.platformbinance.app

import org.freekode.cryptobot.platformbinance.domain.PlatformPriceSubmitter
import org.freekode.cryptobot.platformbinance.domain.MarketPair
import org.freekode.cryptobot.platformbinance.domain.PlatformQuery
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class PlatformSubscriber(
    private val platformQuery: PlatformQuery,
    private val platformPriceSubmitter: PlatformPriceSubmitter
) {
    private val logger: Logger = LoggerFactory.getLogger(PlatformSubscriber::class.java)

    fun subscribeForPlatformPrice(pair: MarketPair) {
        logger.info("Subscribing for $pair")
        platformQuery.priceStream(pair) {
            logger.debug("Event $it")
            platformPriceSubmitter.submitPrice(it)
        }
    }
}
