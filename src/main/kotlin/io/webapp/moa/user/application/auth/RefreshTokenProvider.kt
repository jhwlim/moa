package io.webapp.moa.user.application.auth

import io.webapp.moa.user.domain.model.aggregate.User

interface RefreshTokenProvider {
    fun createRefreshToken(user: User): RefreshToken
}
