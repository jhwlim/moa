package io.webapp.moa.common.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "auth.jwt")
data class AuthJwtProperties(
    val secretKey: String,
    val issuer: String,
    val ttl: Long,
)
