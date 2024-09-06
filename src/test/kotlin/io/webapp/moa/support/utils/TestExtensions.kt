package io.webapp.moa.support.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

fun toDateTime(dateTimeString: String): LocalDateTime {
    return LocalDateTime.parse(dateTimeString, formatter)
}
