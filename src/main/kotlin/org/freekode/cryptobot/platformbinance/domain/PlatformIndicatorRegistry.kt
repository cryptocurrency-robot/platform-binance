package org.freekode.cryptobot.platformbinance.domain

import org.springframework.stereotype.Service


@Service
class PlatformIndicatorRegistry(
    indicators: List<PlatformIndicator>
) {
    private val indicatorMap: Map<IndicatorId, PlatformIndicator> =
        indicators.associateBy { it.getIndicatorId() }

    fun getIndicatorImplementation(indicatorId: IndicatorId): PlatformIndicator {
        return indicatorMap[indicatorId]!!
    }

    fun getAvailableIndicators(): Set<IndicatorId> {
        return indicatorMap.keys
    }
}
