package io.webapp.moa.user.application.dto

import io.webapp.moa.user.application.auth.AccessToken
import io.webapp.moa.user.application.auth.RefreshToken

data class AuthTokens(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken,
)
