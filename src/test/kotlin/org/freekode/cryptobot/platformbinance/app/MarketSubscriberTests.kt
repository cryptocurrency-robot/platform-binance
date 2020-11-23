package org.freekode.cryptobot.platformbinance.app

import org.freekode.cryptobot.platformbinance.domain.MarketPair
import org.freekode.cryptobot.platformbinance.domain.MarketQuery
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@SpringBootTest
@PropertySources(
    PropertySource("classpath:application.properties"),
    PropertySource("file:\${user.home}/platform-binance.properties")
)
class MarketSubscriberTests {
    @MockBean
    private var marketQuery: MarketQuery? = null

    @Autowired
    private var marketSubscriber: MarketSubscriber? = null

    @Test
    fun subscribeForPriceTest() {
        getMockMarketQuery()

        marketSubscriber!!.subscribeForPrice(MarketPair.BTC_USDT)
    }

    private fun getMockMarketQuery() {
        val mock = mock(MarketQuery::class.java)
    }
}
