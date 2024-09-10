package io.webapp.moa.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.webapp.moa.user.application.auth.RefreshToken
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import kotlin.reflect.KClass

@Configuration
class RedisTemplateConfig {

    @Bean
    fun refreshTokenRedisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, RefreshToken> {
        return createRedisTemplate(redisConnectionFactory, RefreshToken::class)
    }

    private fun <T: Any> createRedisTemplate(redisConnectionFactory: RedisConnectionFactory, valueType: KClass<T>): RedisTemplate<String, T> {
        return RedisTemplate<String, T>().apply {
            connectionFactory = redisConnectionFactory
            keySerializer = StringRedisSerializer()

            val objectMapper = ObjectMapper().apply {
                registerModule(
                    KotlinModule.Builder()
                        .withReflectionCacheSize(512)
                        .configure(KotlinFeature.NullToEmptyCollection, true)
                        .configure(KotlinFeature.NullToEmptyMap, true)
                        .configure(KotlinFeature.NullIsSameAsDefault, true)
                        .configure(KotlinFeature.SingletonSupport, true)
                        .configure(KotlinFeature.StrictNullChecks, true)
                        .build()
                )
                findAndRegisterModules()
            }
            valueSerializer = Jackson2JsonRedisSerializer(objectMapper, valueType.java)
        }
    }

}
