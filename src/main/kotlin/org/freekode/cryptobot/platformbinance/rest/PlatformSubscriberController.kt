package org.freekode.cryptobot.platformbinance.rest

import org.freekode.cryptobot.platformbinance.app.PriceSubscriberService
import org.freekode.cryptobot.platformbinance.domain.IndicatorName
import org.freekode.cryptobot.platformbinance.domain.PlatformIndicator
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("platform/stream")
class PlatformSubscriberController(
    private val priceSubscriberService: PriceSubscriberService,
) {
    @PostMapping("subscribe/{indicatorName}")
    fun subscribeForIndicator(@PathVariable indicatorName: IndicatorName) {
        priceSubscriberService.subscribeForIndicator(PlatformIndicator.getByIndicatorName(indicatorName))
    }

    @PostMapping("unsubscribe/{indicatorName}")
    fun unsubscribeForIndicator(@PathVariable indicatorName: IndicatorName) {
        priceSubscriberService.unsubscribeForIndicator(PlatformIndicator.getByIndicatorName(indicatorName))
    }
}
