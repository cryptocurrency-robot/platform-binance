package org.freekode.cryptobot.platformbinance.app

import org.freekode.cryptobot.platformbinance.domain.MarketPair
import org.freekode.cryptobot.platformbinance.domain.PlatformPriceSubmitter
import org.freekode.cryptobot.platformbinance.domain.PlatformQuery
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.Closeable


@Service
class PriceStreamService(
    private val platformQuery: PlatformQuery,
    private val platformPriceSubmitter: PlatformPriceSubmitter
) {
    private val logger: Logger = LoggerFactory.getLogger(PriceStreamService::class.java)

    fun subscribeForPrice(pair: MarketPair): Closeable {
        logger.info("Subscribing for $pair")
        return platformQuery.openPriceStream(pair) {
            platformPriceSubmitter.submitPrice(it)
        }
    }

    fun unsubscribeForPrice(pair: MarketPair) {
        logger.info("Unsubscribing for $pair")
        val priceStream = platformQuery.findPriceStream(pair)
        priceStream?.close()
    }
}
