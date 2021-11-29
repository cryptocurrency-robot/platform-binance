package org.freekode.cryptobot.platformbinance.rest

import org.freekode.cryptobot.platformbinance.domain.ServerTimeQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("platform/serverTime")
class ServerTimeController(
    private val serverTimeQuery: ServerTimeQuery
) {
    @GetMapping
    fun getServerTime(): Long {
        return serverTimeQuery.getServerTime()
    }
}
