package org.freekode.cryptobot.platformbinance.app

import org.freekode.cryptobot.platformbinance.domain.PlatformIndicator
import org.freekode.cryptobot.platformbinance.domain.PlatformValueSubmitter
import org.freekode.cryptobot.platformbinance.domain.PlatformQuery
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.Closeable


@Service
class PriceSubscriberService(
    private val platformQuery: PlatformQuery,
    private val platformValueSubmitter: PlatformValueSubmitter
) {
    private val logger: Logger = LoggerFactory.getLogger(PriceSubscriberService::class.java)

    fun subscribeForIndicator(platformIndicator: PlatformIndicator): Closeable {
        logger.info("Subscribing for $platformIndicator")
        return platformQuery.openPriceStream(platformIndicator) {
            platformValueSubmitter.submitPrice(it)
        }
    }

    fun unsubscribeForIndicator(platformIndicator: PlatformIndicator) {
        logger.info("Unsubscribing for $platformIndicator")
        val priceStream = platformQuery.findPriceStream(platformIndicator)
        priceStream?.close()
    }
}
