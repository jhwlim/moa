package io.webapp.moa.user.infrastructure.auth

import io.webapp.moa.user.application.auth.RefreshTokenProvider
import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.data.RefreshToken
import io.webapp.moa.user.domain.repository.RefreshTokenRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class UuidRefreshTokenProvider(
    private val refreshTokenRepository: RefreshTokenRepository,
) : RefreshTokenProvider {

    override fun createRefreshToken(user: User): RefreshToken {
        return RefreshToken(UUID.randomUUID().toString()).also {
            refreshTokenRepository.save(userId = user.id, refreshToken = it)
        }
    }

}
