package io.webapp.moa

import io.webapp.moa.RedisTestContainer.Companion.REDIS_CONTAINER
import io.webapp.moa.RedisTestContainer.Companion.REDIS_CONTAINER_PORT
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@TestConfiguration
class RedisTestConfig {

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        val redisHost = REDIS_CONTAINER.host
        val redisPort = REDIS_CONTAINER.getMappedPort(REDIS_CONTAINER_PORT)
        return LettuceConnectionFactory(redisHost, redisPort)
    }

}
