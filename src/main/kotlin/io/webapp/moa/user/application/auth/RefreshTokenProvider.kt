package io.webapp.moa.user.application.auth

import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.value.RefreshToken

interface RefreshTokenProvider {
    fun createRefreshToken(user: User): RefreshToken
}
