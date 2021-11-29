package org.freekode.cryptobot.platformbinance.domain

import org.freekode.cryptobot.genericplatformlibrary.domain.PlatformIndicator
import org.freekode.cryptobot.genericplatformlibrary.domain.PlatformIndicatorRegistry
import org.springframework.stereotype.Service

@Service
class BinanceRegistry(
    indicators: List<PlatformIndicator>
) : PlatformIndicatorRegistry(indicators) {
}
