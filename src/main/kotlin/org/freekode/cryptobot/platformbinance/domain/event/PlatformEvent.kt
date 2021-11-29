package org.freekode.cryptobot.platformbinance.domain.event

import java.io.Serializable


data class PlatformEvent(
    val platformId: String,
    val pair: String,
    val indicatorId: String,
    val value: String,
    val timestamp: Long
) : Serializable
