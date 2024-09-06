package io.webapp.moa.user.infrastructure.auth

import io.webapp.moa.user.application.auth.RefreshToken
import io.webapp.moa.user.application.auth.RefreshTokenProvider
import io.webapp.moa.user.domain.model.aggregate.User
import org.springframework.stereotype.Component

@Component
class SimpleTokenProvider : RefreshTokenProvider {

    override fun createRefreshToken(user: User): RefreshToken {
        return RefreshToken("refreshToken")
    }

}
