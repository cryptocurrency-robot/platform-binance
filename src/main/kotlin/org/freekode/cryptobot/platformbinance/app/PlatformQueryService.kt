package org.freekode.cryptobot.platformbinance.app

import org.freekode.cryptobot.platformbinance.domain.PlatformQuery
import org.springframework.stereotype.Service


@Service
class PlatformQueryService(
    private val platformQuery: PlatformQuery
) {
    fun getServerTime(): Long {
        return platformQuery.getServerTime()
    }
}
