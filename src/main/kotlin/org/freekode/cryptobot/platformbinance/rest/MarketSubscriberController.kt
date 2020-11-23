package org.freekode.cryptobot.platformbinance.rest

import org.freekode.cryptobot.platformbinance.app.MarketSubscriber
import org.freekode.cryptobot.platformbinance.domain.MarketPair
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("market/subscriber")
class MarketSubscriberController(
    private val marketSubscriber: MarketSubscriber
) {
    @GetMapping
    fun uploadFile(): String {
        return "OK"
    }

    @PostMapping("/subscribe/{pair}")
    fun subscribeForPair(@PathVariable pair: MarketPair){
        marketSubscriber.subscribeForPrice(pair)
    }
}
