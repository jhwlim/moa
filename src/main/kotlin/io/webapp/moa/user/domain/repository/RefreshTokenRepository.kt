package io.webapp.moa.user.domain.repository

import io.webapp.moa.user.domain.model.data.RefreshToken

interface RefreshTokenRepository {
    fun save(userId: Long, refreshToken: RefreshToken)
    fun find(userId: Long): RefreshToken?
}
