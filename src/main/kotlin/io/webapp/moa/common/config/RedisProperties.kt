package io.webapp.moa.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "redis")
data class RedisProperties(
    val key: Map<String, RedisProperty>,
) {

    fun getProperty(keyType: RedisKeyType): RedisProperty {
        return key[keyType.value] ?: throw IllegalArgumentException("Redis prefix not found for key type: $keyType")
    }

    enum class RedisKeyType(
        val value: String,
    ) {
        REFRESH_TOKEN("refresh-token"),
        ;
    }

    data class RedisProperty(
        val prefix: String,
        val ttl: Long,
    ) {

        val timeout: Duration = Duration.ofSeconds(ttl)

    }

}
