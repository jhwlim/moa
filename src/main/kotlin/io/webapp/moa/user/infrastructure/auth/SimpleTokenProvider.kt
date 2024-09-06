package io.webapp.moa.user.infrastructure.auth

import io.webapp.moa.user.application.auth.AccessToken
import io.webapp.moa.user.application.auth.AccessTokenProvider
import io.webapp.moa.user.application.auth.RefreshToken
import io.webapp.moa.user.application.auth.RefreshTokenProvider
import io.webapp.moa.user.domain.model.aggregate.User
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SimpleTokenProvider : AccessTokenProvider, RefreshTokenProvider {

    override fun createAccessToken(user: User, createdAt: LocalDateTime): AccessToken {
        return AccessToken("accessToken")
    }

    override fun createRefreshToken(user: User): RefreshToken {
        return RefreshToken("refreshToken")
    }

}
