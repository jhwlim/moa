package io.webapp.moa.common.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

private val SEOUL_ZONE_ID: ZoneId = ZoneId.of("Asia/Seoul")

fun LocalDateTime.toDate(): Date {
    val zonedDateTime = this.atZone(SEOUL_ZONE_ID)
    return Date.from(zonedDateTime.toInstant())
}

