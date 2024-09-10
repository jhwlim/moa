package io.webapp.moa.user.infrastructure.cache

import io.webapp.moa.common.config.RedisProperties
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations

abstract class AbstractRedisRepository<T: Any>(
    redisTemplate: RedisTemplate<String, T>,
    redisProperties: RedisProperties,
) {

    abstract val keyType: RedisProperties.RedisKeyType

    private val property: RedisProperties.RedisProperty by lazy { redisProperties.getProperty(keyType) }

    private val opsForValue: ValueOperations<String, T> = redisTemplate.opsForValue()

    protected fun save(key: String, value: T) {
        opsForValue.set(createRedisKey(key), value, property.timeout)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun find(key: String): T? {
        return opsForValue.get(createRedisKey(key)) as? T
    }

    private fun createRedisKey(key: String): String {
        return "${property.prefix}$REDIS_SEPARATOR$key"
    }

    protected fun createKey(vararg ids: String): String {
        return ids.joinToString(REDIS_SEPARATOR)
    }

    companion object {
        private const val REDIS_SEPARATOR = ":"
    }

}
