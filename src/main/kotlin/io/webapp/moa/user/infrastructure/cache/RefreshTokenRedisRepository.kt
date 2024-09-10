package io.webapp.moa.user.infrastructure.cache

import io.webapp.moa.common.config.RedisProperties
import io.webapp.moa.user.domain.model.data.RefreshToken
import io.webapp.moa.user.domain.repository.RefreshTokenRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class RefreshTokenRedisRepository(
    redisTemplate: RedisTemplate<String, RefreshToken>,
    refreshTokenRedisProperties: RedisProperties,
) : AbstractRedisRepository<RefreshToken>(redisTemplate, refreshTokenRedisProperties), RefreshTokenRepository {

    override val keyType = RedisProperties.RedisKeyType.REFRESH_TOKEN

    override fun save(userId: Long, refreshToken: RefreshToken) {
        return save(createKey(userId), refreshToken)
    }

    override fun find(userId: Long): RefreshToken? {
        return find(createKey(userId))
    }

    private fun createKey(userId: Long) = createKey(userId.toString())

}
