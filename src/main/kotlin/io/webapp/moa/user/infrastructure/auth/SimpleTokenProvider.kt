package io.webapp.moa.user.infrastructure.auth

import io.webapp.moa.user.application.auth.AccessTokenProvider
import io.webapp.moa.user.application.auth.RefreshTokenProvider
import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.value.AccessToken
import io.webapp.moa.user.domain.model.value.RefreshToken
import org.springframework.stereotype.Component

@Component
class SimpleTokenProvider : AccessTokenProvider, RefreshTokenProvider {

    override fun createAccessToken(user: User): AccessToken {
        return AccessToken("accessToken")
    }

    override fun createRefreshToken(user: User): RefreshToken {
        return RefreshToken("refreshToken")
    }

}
