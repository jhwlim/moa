package io.webapp.moa.user.application.auth

import io.webapp.moa.user.domain.model.aggregate.User
import org.springframework.security.core.Authentication
import java.time.LocalDateTime

interface AccessTokenProvider {
    fun createAccessToken(user: User, createdAt: LocalDateTime): AccessToken
    fun getAuthentication(accessToken: AccessToken): Authentication
}
