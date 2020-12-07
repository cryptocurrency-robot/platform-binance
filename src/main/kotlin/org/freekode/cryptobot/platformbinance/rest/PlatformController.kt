package org.freekode.cryptobot.platformbinance.rest

import org.freekode.cryptobot.platformbinance.app.PlatformService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("platform/query")
class PlatformController(
    private val platformService: PlatformService
) {
    @GetMapping("serverTime")
    fun getServerTime(): Long {
        return platformService.getServerTime()
    }
}
