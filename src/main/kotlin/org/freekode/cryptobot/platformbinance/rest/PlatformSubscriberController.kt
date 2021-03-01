package org.freekode.cryptobot.platformbinance.rest

import org.freekode.cryptobot.platformbinance.app.PriceSubscriberService
import org.freekode.cryptobot.platformbinance.domain.MarketPair
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("platform/stream")
class PlatformSubscriberController(
    private val priceSubscriberService: PriceSubscriberService,
) {
    @PostMapping("subscribe/{pair}")
    fun subscribeForPair(@PathVariable pair: MarketPair) {
        priceSubscriberService.subscribeForPrice(pair)
    }

    @PostMapping("unsubscribe/{pair}")
    fun unsubscribeForPair(@PathVariable pair: MarketPair) {
        priceSubscriberService.unsubscribeForPrice(pair)
    }
}
