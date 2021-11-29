package org.freekode.cryptobot.platformbinance

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableCaching
@Configuration
class CacheConfiguration {

    @Bean
    fun simpleCacheCustomizer(): CacheManagerCustomizer<ConcurrentMapCacheManager> =
        CacheManagerCustomizer {
            it.setCacheNames(listOf(
                "priceStream",
                "24hPriceChangeStream"
            ))
        }
}
