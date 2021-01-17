package org.freekode.cryptobot.platformbinance

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.stereotype.Component
import java.util.Arrays.asList


@Component
class SimpleCacheCustomizer : CacheManagerCustomizer<ConcurrentMapCacheManager> {
    override fun customize(cacheManager: ConcurrentMapCacheManager) {
        cacheManager.setCacheNames(listOf("priceStream"))
    }
}
