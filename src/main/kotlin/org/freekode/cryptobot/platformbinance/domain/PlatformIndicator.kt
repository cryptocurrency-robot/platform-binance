package org.freekode.cryptobot.platformbinance.domain


enum class PlatformIndicator(
    val indicatorName: IndicatorName,
    val platformSpecificName: String
) {
    BTC_USDT(IndicatorName("BTC_USDT"), "BTCUSDT"),
    ADA_BTC(IndicatorName("ADA_BTC"), "ADABTC");

    companion object {
        fun getByIndicatorName(indicatorName: IndicatorName): PlatformIndicator {
            return values().first { it.indicatorName == indicatorName }
        }
    }
}
