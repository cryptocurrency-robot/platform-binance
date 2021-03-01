package org.freekode.cryptobot.platformbinance.app

import org.freekode.cryptobot.platformbinance.domain.PlatformIndicator
import org.freekode.cryptobot.platformbinance.domain.PlatformQuery
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@SpringBootTest
@PropertySources(
    PropertySource("classpath:application.properties")
)
class MarketSubscriberTests {
    @MockBean
    private var platformQuery: PlatformQuery? = null

    @Autowired
    private var priceSubscriberService: PriceSubscriberService? = null

    @Test
    fun subscribeForPriceTest() {
        getMockMarketQuery()

        priceSubscriberService!!.subscribeForIndicator(PlatformIndicator.BTC_USDT)
    }

    private fun getMockMarketQuery() {
        val mock = mock(PlatformQuery::class.java)
    }
}
