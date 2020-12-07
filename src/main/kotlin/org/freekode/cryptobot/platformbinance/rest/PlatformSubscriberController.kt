package org.freekode.cryptobot.platformbinance.rest

import org.freekode.cryptobot.platformbinance.app.PlatformSubscriber
import org.freekode.cryptobot.platformbinance.domain.MarketPair
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("platform/subscribe")
class PlatformSubscriberController(
    private val platformSubscriber: PlatformSubscriber,
) {
    @PostMapping("{pair}")
    fun subscribeForPair(@PathVariable pair: MarketPair) {
        platformSubscriber.subscribeForPlatformPrice(pair)
    }
}
