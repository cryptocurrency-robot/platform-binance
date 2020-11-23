package org.freekode.cryptobot.platformbinance.app

import org.freekode.cryptobot.platformbinance.domain.EventSubmitter
import org.freekode.cryptobot.platformbinance.domain.MarketPair
import org.freekode.cryptobot.platformbinance.domain.MarketQuery
import org.freekode.cryptobot.platformbinance.infrastructure.BinanceMarketClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class MarketSubscriber(
    private val marketQuery: MarketQuery,
    private val eventSubmitter: EventSubmitter
) {
    private val logger: Logger = LoggerFactory.getLogger(MarketSubscriber::class.java)

    fun subscribeForPrice(pair: MarketPair) {
        logger.info("Subscribing for $pair")
        marketQuery.priceStream(pair) {
            logger.debug("Event $it")
            eventSubmitter.submitPrice(it)
        }
    }
}
